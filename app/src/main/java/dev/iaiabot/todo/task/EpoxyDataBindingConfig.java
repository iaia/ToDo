package dev.iaiabot.todo.task;

import com.airbnb.epoxy.EpoxyDataBindingLayouts;

import dev.iaiabot.todo.R;

@EpoxyDataBindingLayouts({
        R.layout.list_item_task,
        R.layout.list_item_task_incomplete,
        R.layout.list_item_task_completed,
})
interface EpoxyDataBindingConfig {
}
