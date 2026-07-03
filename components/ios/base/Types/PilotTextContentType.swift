import Shared
import SwiftUI

extension PilotTextContentType {
    public var uiTextContentType: UITextContentType? {
        switch self {
        case .notSet:
            return nil
        case .name:
            return .name
        case .namePrefix:
            return .namePrefix
        case .givenName:
            return .givenName
        case .middleName:
            return .middleName
        case .familyName:
            return .familyName
        case .nameSuffix:
            return .nameSuffix
        case .nickname:
            return .nickname
        case .jobTitle:
            return .jobTitle
        case .organizationName:
            return .organizationName
        case .location:
            return .location
        case .fullStreetAddress:
            return .fullStreetAddress
        case .streetAddressLine1:
            return .streetAddressLine1
        case .streetAddressLine2:
            return .streetAddressLine2
        case .addressCity:
            return .addressCity
        case .addressState:
            return .addressState
        case .addressCityAndState:
            return .addressCityAndState
        case .sublocality:
            return .sublocality
        case .countryName:
            return .countryName
        case .postalCode:
            return .postalCode
        case .telephoneNumber:
            return .telephoneNumber
        case .emailAddress:
            return .emailAddress
        case .url:
            return .URL
        case .creditCardNumber:
            return .creditCardNumber
        case .username:
            return .username
        case .password:
            return .password
        case .newPassword:
            return .newPassword
        case .oneTimeCode:
            return .oneTimeCode
        }
    }
}
