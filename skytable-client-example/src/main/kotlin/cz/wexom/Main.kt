package cz.wexom

import cz.wexom.connection.ConnectionBuilder
import cz.wexom.query.Query
import mu.KotlinLogging

val logger = KotlinLogging.logger {}

fun main() {

    val con = ConnectionBuilder()
        .host("127.0.0.1")
        .username("root")
        .password("password1234567890")
        .build()

    logger.trace{String(con.oldQuery("CREATE SPACE IF NOT EXISTS test_space"), Charsets.UTF_8)}
    logger.trace{String(con.oldQuery("CREATE MODEL IF NOT EXISTS test_space.test_model_string(stringKey: string, stringValue: string)"), Charsets.UTF_8)}
    logger.trace{String(con.oldQuery("CREATE MODEL IF NOT EXISTS test_space.test_model_int(intKey: uint64, stringValue: string)"), Charsets.UTF_8)}
    logger.trace{String(con.oldQuery("INSPECT GLOBAL"), Charsets.UTF_8)}
    logger.trace{String(con.oldQuery("INSPECT SPACE test_space"), Charsets.UTF_8)}
    logger.trace{String(con.oldQuery("INSPECT MODEL test_space.test_model_string"), Charsets.UTF_8)}
    logger.trace{String(con.oldQuery("INSPECT MODEL test_space.test_model_int"), Charsets.UTF_8)}
    logger.trace{String(con.oldQuery("DROP MODEL test_space.test_model_string"), Charsets.UTF_8)}
    logger.trace{String(con.oldQuery("DROP MODEL test_space.test_model_int"), Charsets.UTF_8)}
    logger.trace{String(con.oldQuery("DROP SPACE test_space"), Charsets.UTF_8)}

    logger.trace{"---------------------------------------------------------------------------------------------------------"}

    logger.trace{String(con.query(Query("CREATE SPACE IF NOT EXISTS test_space")), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("CREATE MODEL IF NOT EXISTS test_space.test_model_string(stringKey: string, stringValue: string)")), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("CREATE MODEL IF NOT EXISTS test_space.test_model_uint64(intKey: uint64, stringValue: string)")), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("CREATE MODEL IF NOT EXISTS test_space.test_model_sint64(intKey: sint64, stringValue: string)")), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("INSPECT GLOBAL")), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("INSPECT SPACE test_space")), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("INSPECT MODEL test_space.test_model_string")), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("INSPECT MODEL test_space.test_model_int")), Charsets.UTF_8)}

    logger.trace{"#########################################################################################################"}

    logger.trace{String(con.query(Query("INSERT INTO test_space.test_model_uint64(?,?)", 1u, "test1")), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("DELETE FROM test_space.test_model_uint64 WHERE intKey = ?", 1,)), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("INSERT INTO test_space.test_model_sint64(?,?)", -1, "test-1")), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("INSERT INTO test_space.test_model_sint64(?,?)", 1, "test1")), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("DELETE FROM test_space.test_model_sint64 WHERE intKey = ?", -1,)), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("DELETE FROM test_space.test_model_sint64 WHERE intKey = ?", 1,)), Charsets.UTF_8)}

    logger.trace{"#########################################################################################################"}

    logger.trace{String(con.query(Query("DROP MODEL test_space.test_model_string")), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("DROP MODEL test_space.test_model_int")), Charsets.UTF_8)}
    logger.trace{String(con.query(Query("DROP SPACE test_space")), Charsets.UTF_8)}
}