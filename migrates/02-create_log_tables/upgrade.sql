BEGIN;

ALTER TABLE masaca.ingredient_type
    ADD UNIQUE (name);

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
VALUES (4, 'PRE_SHAPE');
INSERT INTO masaca.procedure_step (id, name)
VALUES (5, 'SHAPE');
INSERT INTO masaca.procedure_step (id, name)
VALUES (6, 'COUNTER_PROOF');
INSERT INTO masaca.procedure_step (id, name)
VALUES (7, 'COLD_PROOF');
INSERT INTO masaca.procedure_step (id, name)
VALUES (8, 'STEAM_BAKE');
INSERT INTO masaca.procedure_step (id, name)
VALUES (9, 'DRY_BAKE');
INSERT INTO masaca.procedure_step (id, name)
VALUES (10, 'OTHER');

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

COMMIT;
