package com.mirego.pilot.components.ui.material3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import com.mirego.pilot.components.PilotPicker

@Composable
public fun <LABEL : Any, ITEM : Any> PilotPicker(
    pilotPicker: PilotPicker<LABEL, ITEM>,
    modifier: Modifier = Modifier,
    dropDownModifier: Modifier = Modifier,
    dismissOnItemClick: Boolean = false,
    colors: MenuItemColors = MenuDefaults.itemColors(),
    itemColors: @Composable (label: LABEL) -> Unit,
    item: @Composable (item: ITEM) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Box(
            modifier = modifier
                .clickable(
                    onClick = { expanded = !expanded },
                    role = Role.Button,
                ),
        ) {
            val labelValue by pilotPicker.label.collectAsState()
            itemColors(labelValue)
        }
        // button unselected
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = dropDownModifier,
        ) {
            val itemsValue by pilotPicker.items.collectAsState()
            itemsValue.forEachIndexed { index, itemValue ->
                DropdownMenuItem(
                    text = {
                        item(itemValue)
                    },
                    onClick = {
                        pilotPicker.onSelectedIndex(index)
                        if (dismissOnItemClick) {
                            expanded = false
                        }
                    },
                    colors = colors,
                )
            }
        }
    }
}
