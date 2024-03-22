import Shared
import SwiftUI

public struct PilotPickerView<Label, LabelView, Item, ItemView>: View where Label: AnyObject, LabelView: View, Item: AnyObject, ItemView: View {

    private let picker: PilotPicker<Label, Item>
    private let labelBuilder: (Label) -> LabelView
    private let itemBuilder: (Item) -> ItemView

    @ObservedObject private var label: StateObservable<Label>
    @ObservedObject private var items: StateObservable<[Item]>

    public init(
        _ picker: PilotPicker<Label, Item>,
        labelBuilder: @escaping (Label) -> LabelView,
        itemBuilder: @escaping (Item) -> ItemView
    ) {
        self.picker = picker
        self.labelBuilder = labelBuilder
        self.itemBuilder = itemBuilder

        _label = ObservedObject(wrappedValue: StateObservable(picker.label))
        _items = ObservedObject(wrappedValue: StateObservable(picker.items))
    }

    public var body: some View {
        Menu {
            ForEach(items.value.indices, id: \.self) { index in
                Button {
                    picker.onSelectedIndex(KotlinInt(int: Int32(index)))
                } label: {
                    itemBuilder(items.value[index])
                }
            }
        } label: {
            labelBuilder(label.value)
        }
    }
}
