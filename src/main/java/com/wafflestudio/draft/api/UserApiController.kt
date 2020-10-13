package com.wafflestudio.draft.api

import com.wafflestudio.draft.dto.request.GetUsersByPreferenceRequest
import com.wafflestudio.draft.dto.request.SetDeviceRequest
import com.wafflestudio.draft.dto.request.SetPreferenceRequest
import com.wafflestudio.draft.dto.request.SignUpRequest
import com.wafflestudio.draft.dto.response.DeviceResponse
import com.wafflestudio.draft.dto.response.PreferenceInRegionResponse
import com.wafflestudio.draft.dto.response.UserInformationResponse
import com.wafflestudio.draft.model.Device
import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.security.CurrentUser
import com.wafflestudio.draft.security.JwtTokenProvider
import com.wafflestudio.draft.security.oauth2.AuthUserService
import com.wafflestudio.draft.security.oauth2.OAuth2Provider
import com.wafflestudio.draft.security.password.UserPrincipal
import com.wafflestudio.draft.service.DeviceService
import com.wafflestudio.draft.service.PreferenceService
import com.wafflestudio.draft.service.RegionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.io.IOException
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserApiController(private val oAuth2Provider: OAuth2Provider, private val authUserService: AuthUserService, private val passwordEncoder: PasswordEncoder, private val regionService: RegionService, private val preferenceService: PreferenceService, private val deviceService: DeviceService, private val jwtTokenProvider: JwtTokenProvider) {
    @PostMapping("/signup/")
    @Throws(IOException::class)
    fun createUser(@RequestBody signUpRequest: SignUpRequest, response: HttpServletResponse): ResponseEntity<User>? {
        val user: User
        when (signUpRequest.grantType) {
            "OAUTH" -> {
                val oAuth2Response = oAuth2Provider.requestAuthentication(signUpRequest)
                if (oAuth2Response!!.status != HttpStatus.OK) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token for auth server is not valid")
                    return null
                }
                user = User(signUpRequest.username, oAuth2Response.email)
            }
            "PASSWORD" -> {
                if (signUpRequest.username == null || signUpRequest.email == null || signUpRequest.password == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST)
                    return null
                }
                user = User(signUpRequest.username, signUpRequest.email)
                user.password = passwordEncoder.encode(signUpRequest.password)
            }
            else -> {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Grant type not valid")
                return null
            }
        }
        authUserService.saveUser(user)
        response.addHeader("Authentication", jwtTokenProvider.generateToken(user.email))
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    //    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me/")
    fun myInfo(@CurrentUser currentUser: UserPrincipal): ResponseEntity<UserInformationResponse> {
        return ResponseEntity(UserInformationResponse(currentUser), HttpStatus.OK)
    }

    //    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/info/")
    fun setPreferences(@Valid @RequestBody preferenceRequestList: List<SetPreferenceRequest>, @CurrentUser currentUser: UserPrincipal): List<PreferenceInRegionResponse> {
        val preferenceInRegionResponses: MutableList<PreferenceInRegionResponse> = ArrayList()
        for (preferenceRequest in preferenceRequestList) {
            val region = regionService.findRegionById(preferenceRequest.regionId)
            if (region!!.isEmpty) {
                // FIXME: when a region is not found, we should not apply whole preferences of the request
                throw ResponseStatusException(HttpStatus.NOT_FOUND)
            }
            val preferences = preferenceRequest.preferences
            preferenceService.setPreferences(currentUser, region.get(), preferences)
            preferenceInRegionResponses.add(PreferenceInRegionResponse(region.get(), preferences))
        }
        return preferenceInRegionResponses
    }

    @GetMapping("/playable/")
    fun getPlayableUsers(@Valid @ModelAttribute getUsersByPreferenceRequest: GetUsersByPreferenceRequest): List<Long?>? {
        val regionName = getUsersByPreferenceRequest.regionName
        val dayOfWeek = getUsersByPreferenceRequest.dayOfWeek
        val startTime = getUsersByPreferenceRequest.startTime
        val endTime = getUsersByPreferenceRequest.endTime
        return preferenceService.getPlayableUsers(regionName, dayOfWeek, startTime, endTime)
    }

    @PostMapping("/device/")
    @ResponseStatus(HttpStatus.CREATED)
    fun setDevice(@RequestBody @Valid request: SetDeviceRequest, @CurrentUser currentUser: User?): DeviceResponse {
        val device = Device()
        device.user = currentUser
        device.deviceToken = request.deviceToken
        deviceService.create(device)
        return DeviceResponse(device)
    }

}