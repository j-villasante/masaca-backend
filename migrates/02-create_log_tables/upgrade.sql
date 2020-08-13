BEGIN;

CREATE TABLE masaca.procedure
(
    id         SERIAL PRIMARY KEY,
    recipe_id  INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    CONSTRAINT fk_procedure_recipe_id_recipe
        FOREIGN KEY (recipe_id)
            REFERENCES masaca.recipe (id)
);

CREATE TABLE masaca.procedure_step
(
    id   SMALLINT PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

INSERT INTO masaca.procedure_step (id, name)
VALUES (1, 'LEVAIN');
INSERT INTO masaca.procedure_step (id, name)
VALUES (2, 'AUTOLYSIS');
INSERT INTO masaca.procedure_step (id, name)
VALUES (3, 'BULK_FERMENTATION');
INSERT INTO masaca.procedure_step (id, name)
VALUES (4, 'STRETCH_AND_FOLD');
INSERT INTO masaca.procedure_step (id, name)
VALUES (5, 'PRE_SHAPE');
INSERT INTO masaca.procedure_step (id, name)
VALUES (6, 'SHAPE');
INSERT INTO masaca.procedure_step (id, name)
VALUES (7, 'COUNTER_PROOF');
INSERT INTO masaca.procedure_step (id, name)
VALUES (8, 'COLD_PROOF');
INSERT InTO masaca.procedure_step (id, name)
VALUES (9, 'STEAM_BAKE');
INSERT INTO masaca.procedure_step (id, name)
VALUES (10, 'DRY_BAKE');
INSERT INTO masaca.procedure_step (id, name)
VALUES (11, 'OTHER');

CREATE TABLE masaca.procedure_step_log
(
    id           SERIAL PRIMARY KEY,
    procedure_id INTEGER  NOT NULL,
    step_id      SMALLINT NOT NULL,
    step_other   VARCHAR,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT now(),
    CONSTRAINT fk_procedure_step_log_step_id_procedure_step
        FOREIGN KEY (step_id)
            REFERENCES masaca.procedure_step (id),
    CONSTRAINT fk_procedure_step_log_procedure_id_procedure
        FOREIGN KEY (procedure_id)
            REFERENCES masaca.procedure (id)
);

CREATE TABLE masaca.procedure_temperature_log
(
    id           SERIAL PRIMARY KEY,
    procedure_id INTEGER  NOT NULL,
    temperature  SMALLINT NOT NULL,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT now(),
    CONSTRAINT fk_procedure_temperature_log_procedure_id_procedure
        FOREIGN KEY (procedure_id)
            REFERENCES masaca.procedure (id)
);

GRANT USAGE ON ALL SEQUENCES IN SCHEMA masaca TO masaca_api;
GRANT SELECT ON masaca.ingredient_type TO masaca_api;
GRANT SELECT, INSERT ON masaca.procedure TO masaca_api;
GRANT SELECT ON masaca.procedure_step TO masaca_api;
GRANT SELECT, INSERT ON masaca.procedure_temperature_log TO masaca_api;
GRANT SELECT, INSERT ON masaca.procedure_step_log TO masaca_api;

ALTER TABLE masaca.ingredient_type
    ADD UNIQUE (name);

ALTER TABLE masaca.ingredient
    ADD COLUMN number INTEGER;

WITH a AS (
    SELECT id,
           row_number()
           OVER (PARTITION BY recipe_id ORDER BY id) AS number
    FROM masaca.ingredient
)
UPDATE masaca.ingredient i
SET number = a.number
FROM a
WHERE a.id = i.id;

ALTER TABLE masaca.ingredient
    ALTER COLUMN number SET NOT NULL;

ALTER TABLE masaca.ingredient
    ADD UNIQUE (number, recipe_id);

COMMIT;
