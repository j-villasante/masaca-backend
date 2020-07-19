BEGIN;

CREATE TABLE masaca.ingredient_type (
    id SMALLINT PRIMARY KEY,
    name VARCHAR NOT NULL
);
INSERT INTO masaca.ingredient_type (id, name) VALUES (1, 'FLOUR');
INSERT INTO masaca.ingredient_type (id, name) VALUES (2, 'WET');
INSERT INTO masaca.ingredient_type (id, name) VALUES (3, 'DRY');
INSERT INTO masaca.ingredient_type (id, name) VALUES (4, 'LEVAIN');

CREATE TABLE masaca.recipe (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TABLE masaca.ingredient (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    amount NUMERIC (6, 2),
    percentage NUMERIC (6, 2),
    cost NUMERIC (6, 2),
    type_id SMALLINT NOT NULL,
    recipe_id INTEGER NOT NULL,
    CONSTRAINT fk_ingredient_type_id_ingredient_type
        FOREIGN KEY (type_id)
        REFERENCES masaca.ingredient_type(id),
    CONSTRAINT fk_ingredient_recipe_id_recipe
        FOREIGN KEY (recipe_id)
        REFERENCES masaca.recipe(id)
);

COMMIT;
