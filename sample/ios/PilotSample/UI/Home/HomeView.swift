import Pilot
import Shared
import SwiftUI

struct HomeView: View {
    let viewModel: HomeViewModel

    var body: some View {
        VStack(spacing: 16) {
            PilotButtonView(viewModel.textFieldPage) { content in
                Text(String(content))
            }
        }
        .padding()
        .navigation(
            navigationManager: viewModel.navigationManager,
            embedInNavigationView: true
        )
    }
}

#Preview {
    HomeView(viewModel: HomeViewModelImpl())
}
