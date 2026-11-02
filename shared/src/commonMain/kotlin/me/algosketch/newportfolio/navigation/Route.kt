package me.algosketch.newportfolio.navigation

/** 해시 라우팅 경로. `/#/` → [Home], `/#/projects` → [Projects] */
enum class Route(val path: String) {
    Home("/"),
    Projects("/projects"),
    ;

    /** `location.hash` 에 넣을 값 (예: `#/projects`) */
    val hash: String get() = "#$path"

    companion object {
        /** `location.hash` 값을 경로로 바꾼다. 모르는 경로는 [Home] 으로 본다. */
        fun fromHash(hash: String): Route {
            val path = hash.removePrefix("#").substringBefore('?').ifEmpty { "/" }
            val normalized = if (path.length > 1) path.trimEnd('/') else path
            return entries.firstOrNull { it.path == normalized } ?: Home
        }
    }
}
