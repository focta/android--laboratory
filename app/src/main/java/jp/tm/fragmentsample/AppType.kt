package jp.tm.fragmentsample

enum class AppType (val appIndex: Int, val appName: String) {
    // appIndexは作った順にEnumを設定している
    TODO_APP(0, "TODO");
    companion object {
        // 逆引きできるようにcompanion objectで設定
        fun getAppNameByPositionIndex(appIndex: Int): AppType = values().find { it.appIndex == appIndex } ?: throw NullPointerException("AppType")
    }
}