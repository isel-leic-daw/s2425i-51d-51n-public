package pt.isel.daw.tictactoe.repository.jdbi

import kotlinx.datetime.Instant
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import pt.isel.daw.tictactoe.domain.users.PasswordValidationInfo
import pt.isel.daw.tictactoe.domain.users.TokenValidationInfo
import pt.isel.daw.tictactoe.repository.jdbi.mappers.BoardMapper
import pt.isel.daw.tictactoe.repository.jdbi.mappers.InstantMapper
import pt.isel.daw.tictactoe.repository.jdbi.mappers.PasswordValidationInfoMapper
import pt.isel.daw.tictactoe.repository.jdbi.mappers.TokenValidationInfoMapper

fun Jdbi.configureWithAppRequirements(): Jdbi {
    installPlugin(KotlinPlugin())
    installPlugin(PostgresPlugin())

    registerColumnMapper(PasswordValidationInfo::class.java, PasswordValidationInfoMapper())
    registerColumnMapper(TokenValidationInfo::class.java, TokenValidationInfoMapper())
    registerColumnMapper(BoardMapper())
    registerColumnMapper(Instant::class.java, InstantMapper())

    return this
}