package masaca.backend.procedure

import masaca.backend.domain.procedure.*
import masaca.backend.error.*
import java.sql.*
import java.time.*

class Repository(private val connection: Connection) {
    fun createProcedure(procedure: Procedure): Int {
        return connection.prepareStatement(
            "INSERT INTO masaca.procedure (recipe_id) VALUES (?) RETURNING id"
        ).apply {
            setInt(1, procedure.recipeId)
        }.executeQuery().let {
            if (it.next())
                it.getInt(1)
            else
                throw MasacaCreationError("Could not create procedure")
        }
    }

    fun createStepLog(stepLog: ProcedureStepLog): Int {
        return connection.prepareStatement(
            "INSERT INTO masaca.procedure_step_log (procedure_id, step_id, step_other) VALUES (?, ?, ?) RETURNING id"
        ).apply {
            setInt(1, stepLog.procedureId)
            setInt(2, stepLog.step.id)
            setObject(3, stepLog.stepOther, Types.VARCHAR)
        }.executeQuery().let {
            if (it.next())
                it.getInt(1)
            else
                throw MasacaCreationError("Could not create log")
        }
    }

    fun createTemperatureLog(temperatureLog: ProcedureTemperatureLog): Int {
        return connection.prepareStatement(
            "INSERT INTO masaca.procedure_temperature_log (procedure_id, temperature) VALUES (?, ?) RETURNING id"
        ).apply {
            setInt(1, temperatureLog.procedureId)
            setInt(2, temperatureLog.temperature)
        }.executeQuery().let {
            if (it.next())
                it.getInt(1)
            else
                throw MasacaCreationError("Could not create temperature log")
        }
    }

    fun getProcedure(procedureId: Int): Procedure {
        val procedure = connection.prepareStatement(
            "SELECT recipe_id, created_at FROM masaca.procedure WHERE id = ?"
        ).apply {
            setInt(1, procedureId)
        }.executeQuery().let {
            if (it.next())
                Procedure(
                    procedureId,
                    it.getInt(1),
                    it.getObject(2, OffsetDateTime::class.java)
                )
            else
                throw MasacaNotFoundError("No procedure was found")
        }
        connection.prepareStatement(
            "SELECT psl.id, ps.name, psl.step_other, psl.created_at FROM masaca.procedure_step_log psl INNER JOIN masaca.procedure_step ps ON psl.step_id = ps.id WHERE procedure_id = ? ORDER BY psl.created_at"
        ).apply {
            setInt(1, procedureId)
        }.executeQuery().let {
            while (it.next()) {
                procedure.stepLogs.add(
                    ProcedureStepLog(
                        it.getInt(1),
                        procedureId,
                        ProcedureStep.valueOf(it.getString(2)),
                        it.getObject(3, String::class.java),
                        it.getObject(4, OffsetDateTime::class.java)
                    )
                )
            }
        }
        connection.prepareStatement(
            "SELECT ptl.id, ptl.temperature, ptl.created_at FROM masaca.procedure_temperature_log ptl WHERE procedure_id = ? ORDER BY ptl.created_at"
        ).apply {
            setInt(1, procedureId)
        }.executeQuery().let {
            while (it.next()) {
                procedure.temperatureLogs.add(
                    ProcedureTemperatureLog(
                        it.getInt(1),
                        procedureId,
                        it.getInt(2),
                        it.getObject(3, OffsetDateTime::class.java)
                    )
                )
            }
        }
        return procedure
    }
}
