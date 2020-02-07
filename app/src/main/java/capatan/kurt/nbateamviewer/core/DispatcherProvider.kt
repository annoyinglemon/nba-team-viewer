package capatan.kurt.nbateamviewer.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

open class DispatcherProvider {

    open val IO: CoroutineDispatcher by lazy {
        Dispatchers.IO
    }

    open val Main: CoroutineDispatcher by lazy {
        Dispatchers.Main
    }

    open val Default: CoroutineDispatcher by lazy {
        Dispatchers.Default
    }

}