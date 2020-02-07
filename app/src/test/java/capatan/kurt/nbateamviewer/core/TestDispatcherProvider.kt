package capatan.kurt.nbateamviewer.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider(private val testDispatcher: TestCoroutineDispatcher): DispatcherProvider() {

    override val IO: CoroutineDispatcher
        get() = testDispatcher

    override val Main: CoroutineDispatcher
        get() = testDispatcher

    override val Default: CoroutineDispatcher
        get() = testDispatcher

}