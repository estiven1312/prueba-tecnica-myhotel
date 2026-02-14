CREATE TABLE IF NOT EXISTS vehicles
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    brand
    VARCHAR
(
    100
) NOT NULL,
    model VARCHAR
(
    100
) NOT NULL,
    license_plate VARCHAR
(
    20
) NOT NULL,
    year BIGINT NOT NULL,
    vehicle_type VARCHAR
(
    50
) NOT NULL,
    cubic_capacity DECIMAL
(
    10,
    2
) NOT NULL,
    mileage DECIMAL
(
    15,
    2
) NOT NULL,
    url_photo VARCHAR
(
    500
),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR
(
    100
),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR
(
    100
),
    deleted_at TIMESTAMP NULL,
    deleted_by VARCHAR
(
    100
),
    INDEX idx_license_plate
(
    license_plate
),
    INDEX idx_vehicle_type
(
    vehicle_type
),
    INDEX idx_brand
(
    brand
),
    INDEX idx_deleted_at
(
    deleted_at
)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS automobiles
(
    id
    BIGINT
    PRIMARY
    KEY,
    automobile_type
    VARCHAR
(
    50
) NOT NULL,
    number_of_doors BIGINT NOT NULL,
    passenger_capacity BIGINT NOT NULL,
    trunk_capacity BIGINT NOT NULL,
    FOREIGN KEY
(
    id
) REFERENCES vehicles
(
    id
) ON DELETE CASCADE
    ) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS lorries
(
    id
    BIGINT
    PRIMARY
    KEY,
    lorry_type
    VARCHAR
(
    50
) NOT NULL,
    axis_number BIGINT NOT NULL,
    tonnage_capacity BIGINT NOT NULL,
    FOREIGN KEY
(
    id
) REFERENCES vehicles
(
    id
) ON DELETE CASCADE
    ) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS maintenances
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    vehicle_id
    BIGINT
    NOT
    NULL,
    description
    TEXT
    NOT
    NULL,
    scheduled_date
    DATE
    NOT
    NULL,
    start_date_time
    TIMESTAMP
    NULL,
    end_date_time
    TIMESTAMP
    NULL,
    kilometers_at_maintenance
    DECIMAL
(
    15,
    2
),
    engine_status VARCHAR
(
    50
),
    brakes_status VARCHAR
(
    50
),
    tires_status VARCHAR
(
    50
),
    transmission_status VARCHAR
(
    50
),
    electrical_status VARCHAR
(
    50
),
    type VARCHAR
(
    50
) NOT NULL,
    comments TEXT,
    status VARCHAR
(
    50
) NOT NULL DEFAULT 'SCHEDULED',

    -- Audit fields
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR
(
    100
),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR
(
    100
),
    deleted_at TIMESTAMP NULL,
    deleted_by VARCHAR
(
    100
),
    FOREIGN KEY
(
    vehicle_id
) REFERENCES vehicles
(
    id
)
                                                   ON DELETE RESTRICT,
    INDEX idx_vehicle_id
(
    vehicle_id
),
    INDEX idx_scheduled_date
(
    scheduled_date
),
    INDEX idx_status
(
    status
),
    INDEX idx_type
(
    type
),
    INDEX idx_deleted_at
(
    deleted_at
)
    ) ENGINE=InnoDB;

