package graphqlkotlinvalueclassproblem

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.reflect.KFunction
import kotlin.reflect.full.callSuspendBy

fun main(args: Array<String>): Unit = runBlocking {
    val fnGood: KFunction<*> = ::suspendAndReturnNormalString
    println(fnGood.callSuspendBy(emptyMap()))
    val fnBad: KFunction<*> = ::suspendAndReturnValueString
    println(fnBad.callSuspendBy(emptyMap()))
}

@JvmInline
value class ValueString(val value: String) {
    override fun toString(): String = value
}

suspend fun suspendAndReturnValueString(): ValueString {
    yield()
    return ValueString("x")
}
suspend fun suspendAndReturnNormalString(): String {
    yield()
    return "x"
}
