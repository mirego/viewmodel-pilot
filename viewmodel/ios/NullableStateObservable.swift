import PILOT_FRAMEWORK_NAME
import SwiftUI

public class NullableStateObservable<State>: ObservableObject {
    private let valueHolder: StateObservableValueHolder<State>
    private let animation: ((State?, State?) -> Animation?)?

    private var task: Task<Void, Never>?
    private var lastState: State?

    public var value: State? {
        switch valueHolder {
        case .const(let state):
            state
        case .stateFlow(let stateFlow):
            stateFlow.value
        case .flow:
            lastState
        }
    }

    public init(_ state: State?) {
        self.valueHolder = StateObservableValueHolder.const(state)
        self.animation = nil
        self.lastState = state
    }

    public init(_ stateFlow: SkieSwiftOptionalStateFlow<State>, animation: ((State?, State?) -> Animation?)? = nil) {
        self.valueHolder = StateObservableValueHolder.stateFlow(stateFlow)
        self.animation = animation
        self.lastState = stateFlow.value

        task = Task { [weak self] in
            for await newState in stateFlow.dropFirst() {
                await self?.apply(newState)
            }
        }
    }

    public init(_ flow: SkieSwiftOptionalFlow<State>, initialValue: State? = nil, animation: ((State?, State?) -> Animation?)? = nil) {
        self.valueHolder = StateObservableValueHolder.flow(flow)
        self.animation = animation
        self.lastState = initialValue

        task = Task { [weak self] in
            for await newState in flow {
                await self?.apply(newState)
            }
        }
    }

    @MainActor
    private func apply(_ newState: State?) {
        if let animation = animation?(lastState, newState) {
            withAnimation(animation) {
                objectWillChange.send()
            }
        } else {
            objectWillChange.send()
        }
        lastState = newState
    }

    deinit {
        task?.cancel()
    }
}

private enum StateObservableValueHolder<State> {
    case const(State?)
    case stateFlow(SkieSwiftOptionalStateFlow<State>)
    case flow(SkieSwiftOptionalFlow<State>)
}
