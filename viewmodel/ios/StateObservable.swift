import Shared
import SwiftUI

public class StateObservable<State>: ObservableObject {
    private let valueHolder: StateObservableValueHolder<State>
    private let animation: ((State, State) -> Animation?)?

    private var task: Task<Void, Never>?
    private var lastState: State

    public var value: State {
        switch valueHolder {
        case .const(let state):
            state
        case .flow(let stateFlow):
            stateFlow.value
        }
    }

    public init(_ state: State) {
        self.valueHolder = StateObservableValueHolder.const(state)
        self.animation = nil
        self.lastState = state
    }

    public init(_ stateFlow: SkieSwiftStateFlow<State>, animation: ((State, State) -> Animation?)? = nil) {
        self.valueHolder = StateObservableValueHolder.flow(stateFlow)
        self.animation = animation
        self.lastState = stateFlow.value

        task = Task { [weak self] in
            for await newState in stateFlow.dropFirst() {
                if let lastState = self?.lastState, let animation = self?.animation?(lastState, newState) {
                    DispatchQueue.main.async {
                        withAnimation(animation) {
                            self?.objectWillChange.send()
                        }
                    }
                } else {
                    DispatchQueue.main.async {
                        self?.objectWillChange.send()
                    }
                }
                self?.lastState = newState
            }
        }
    }

    deinit {
        task?.cancel()
    }
}

private enum StateObservableValueHolder<State> {
    case const(State)
    case flow(SkieSwiftStateFlow<State>)
}
