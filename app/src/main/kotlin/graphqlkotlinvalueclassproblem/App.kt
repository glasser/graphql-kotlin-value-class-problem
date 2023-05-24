package graphqlkotlinvalueclassproblem

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.generator.scalars.IDValueUnboxer
import com.expediagroup.graphql.generator.toSchema
import graphql.GraphQL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun main(args: Array<String>) {
    val schema = toSchema(
        config = SchemaGeneratorConfig(
            supportedPackages = listOf(""),
        ),
        queries = listOf(TopLevelObject(Query())),
    )

    val graphql = GraphQL.newGraphQL(schema).let { builder ->
        builder.valueUnboxer(IDValueUnboxer())
        builder.build()
    }

    println(graphql.execute("{ id string dispatchedString }"))
    println(graphql.execute("{ dispatchedId }"))
}

class Query {
    suspend fun string(): String = "x"
    suspend fun dispatchedString(): String = withContext(Dispatchers.IO) { "x" }
    suspend fun id(): ID = ID("x")
    suspend fun dispatchedId(): ID = withContext(Dispatchers.IO) { ID("x") }
}

