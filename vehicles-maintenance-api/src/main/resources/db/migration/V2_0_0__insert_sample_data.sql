-- =====================================================
-- Sample data for Vehicles Maintenance API
-- =====================================================

INSERT INTO vehicles (brand, model, license_plate, year, vehicle_type, cubic_capacity, mileage, url_photo, created_by)
VALUES ('Toyota', 'Corolla', 'ABC-123', 2022, 'AUTOMOBILE', 1800.00, 15000.00, 'https://example.com/corolla.jpg',
        'system'),
       ('Honda', 'Civic', 'XYZ-789', 2021, 'AUTOMOBILE', 2000.00, 25000.00, 'https://example.com/civic.jpg', 'system');

INSERT INTO automobiles (id, automobile_type, number_of_doors, passenger_capacity, trunk_capacity)
SELECT id, 'SEDAN', 4, 5, 450
FROM vehicles
WHERE license_plate = 'ABC-123'
UNION ALL
SELECT id, 'SEDAN', 4, 5, 420
FROM vehicles
WHERE license_plate = 'XYZ-789';

INSERT INTO vehicles (brand, model, license_plate, year, vehicle_type, cubic_capacity, mileage, url_photo, created_by)
VALUES ('Volvo', 'FH16', 'TRK-001', 2020, 'LORRY', 16000.00, 120000.00, 'https://example.com/volvo.jpg', 'system'),
       ('Scania', 'R450', 'TRK-002', 2019, 'LORRY', 13000.00, 180000.00, 'https://example.com/scania.jpg', 'system');

INSERT INTO lorries (id, lorry_type, axis_number, tonnage_capacity)
SELECT id, 'SEMI_TRAILER', 3, 25
FROM vehicles
WHERE license_plate = 'TRK-001'
UNION ALL
SELECT id, 'TRAILER', 2, 18
FROM vehicles
WHERE license_plate = 'TRK-002';


