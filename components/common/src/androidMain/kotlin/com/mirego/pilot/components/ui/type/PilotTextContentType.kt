package com.mirego.pilot.components.ui.type

import androidx.compose.ui.autofill.ContentType
import com.mirego.pilot.components.InternalPilotComponentsApi
import com.mirego.pilot.components.type.PilotTextContentType

@InternalPilotComponentsApi
public val PilotTextContentType.composeValue: ContentType?
    get() = when (this) {
        PilotTextContentType.NotSet -> null
        PilotTextContentType.Name -> ContentType.PersonFullName
        PilotTextContentType.NamePrefix -> ContentType.PersonNamePrefix
        PilotTextContentType.GivenName -> ContentType.PersonFirstName
        PilotTextContentType.MiddleName -> ContentType.PersonMiddleName
        PilotTextContentType.FamilyName -> ContentType.PersonLastName
        PilotTextContentType.NameSuffix -> ContentType.PersonNameSuffix
        PilotTextContentType.Nickname -> ContentType.PersonFullName
        PilotTextContentType.JobTitle -> null
        PilotTextContentType.OrganizationName -> null
        PilotTextContentType.Location -> null
        PilotTextContentType.FullStreetAddress -> ContentType.AddressStreet
        PilotTextContentType.StreetAddressLine1 -> ContentType.AddressStreet
        PilotTextContentType.StreetAddressLine2 -> ContentType.AddressAuxiliaryDetails
        PilotTextContentType.AddressCity -> ContentType.AddressLocality
        PilotTextContentType.AddressState -> ContentType.AddressRegion
        PilotTextContentType.AddressCityAndState -> ContentType.AddressLocality + ContentType.AddressRegion
        PilotTextContentType.Sublocality -> ContentType.AddressLocality
        PilotTextContentType.CountryName -> ContentType.AddressCountry
        PilotTextContentType.PostalCode -> ContentType.PostalCode
        PilotTextContentType.TelephoneNumber -> ContentType.PhoneNumber
        PilotTextContentType.EmailAddress -> ContentType.EmailAddress
        PilotTextContentType.URL -> null
        PilotTextContentType.CreditCardNumber -> ContentType.CreditCardNumber
        PilotTextContentType.Username -> ContentType.Username
        PilotTextContentType.Password -> ContentType.Password
        PilotTextContentType.NewPassword -> ContentType.NewPassword
        PilotTextContentType.OneTimeCode -> ContentType.SmsOtpCode
    }
