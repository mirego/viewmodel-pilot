import Shared
import SwiftUI

@main
struct PilotSampleApp: App {
    private let homeViewModel = HomeViewModelImpl()

    var body: some Scene {
        WindowGroup {
            HomeView(viewModel: homeViewModel)
        }
    }
}
