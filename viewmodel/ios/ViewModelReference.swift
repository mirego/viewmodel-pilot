import Shared
import SwiftUI

public class ViewModelLifecycleHandler<T: PilotViewModel>: ObservableObject {
    public let viewModel: T

    public init(viewModel: T) {
        self.viewModel = viewModel
    }

    deinit {
        viewModel.clear()
    }
}
