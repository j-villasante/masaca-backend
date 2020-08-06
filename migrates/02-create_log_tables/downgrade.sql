BEGIN;

DROP TABLE masaca.procedure_temperature_log;
DROP TABLE masaca.procedure_step_log;
DROP TABLE masaca.procedure_step;
DROP TABLE masaca.procedure;

ALTER TABLE masaca.ingredient_type
    DROP CONSTRAINT ingredient_type_name_key;

COMMIT;
