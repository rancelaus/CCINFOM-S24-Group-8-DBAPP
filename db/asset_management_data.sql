-- -----------------------------------------------------------------
-- SAMPLE DATA SET FOR IT ASSET AND INVENTORY MANAGEMENT DATABASE
-- -----------------------------------------------------------------

USE asset_management_db;

INSERT INTO Employee (firstName, lastName, department, position, contactNumber, email) VALUES
('Ethan', 'Valdez', 'IT Operations', 'IT Operations Engineer', '09170122345', 'ethan.valdez@novalink.com'),
('Carla', 'Montenegro', 'IT Asset Management', 'Asset Management Analyst', '09171239485', 'carla.montenegro@novalink.com'),
('Julian', 'Chavez', 'Procurement', 'Procurement Specialist', '09172349851', 'julian.chavez@novalink.com'),
('Bianca', 'Lopez', 'Cybersecurity', 'Information Security Officer', '09173458912', 'bianca.lopez@novalink.com'),
('Miguel', 'Soriano', 'Infrastructure Services', 'Systems Administrator', '09174567823', 'miguel.soriano@novalink.com'),
('Leah', 'Domingo', 'Network Services', 'Network Support Engineer', '09175678934', 'leah.domingo@novalink.com'),
('Patrick', 'Castro', 'IT Service Desk', 'Service Desk Technician', '09176789345', 'patrick.castro@novalink.com'),
('Rina', 'Aquino', 'Facilities & Logistics', 'Warehouse & Logistics Coordinator', '09177893456', 'rina.aquino@novalink.com'),
('Harold', 'Vergara', 'Finance & Compliance', 'Compliance and Audit Associate', '09178934567', 'harold.vergara@novalink.com'),
('Nadine', 'Santiago', 'Business Solutions', 'Business Systems Analyst', '09179012345', 'nadine.santiago@novalink.com');

INSERT INTO Asset (expiryDate, purchaseDate, a_status, assetType) VALUES
('2027-05-10', '2024-05-10', 'active', 'hardware'),
('2026-11-22', '2023-11-22', 'active', 'software'),
('2025-09-30', '2022-09-30', 'expired', 'hardware'),
('2028-02-18', '2024-02-18', 'active', 'software'),
('2024-12-01', '2021-12-01', 'damaged', 'hardware'),
('2027-07-14', '2023-07-14', 'active', 'hardware'),
('2025-04-05', '2022-04-05', 'returned', 'software'),
('2029-01-10', '2024-01-10', 'active', 'software'),
('2024-08-19', '2021-08-19', 'lost', 'hardware'),
('2026-03-25', '2023-03-25', 'inactive', 'software');

INSERT INTO HardwareAsset (H_assetID, h_type, brand, model, serialNumber) VALUES
(1, 'Laptop', 'Dell', 'Latitude 5440', 'SN-A1X45D9'),
(3, 'Desktop', 'Lenovo', 'ThinkCentre M720', 'SN-HW93KD2'),
(5, 'Router', 'TP-Link', 'Archer AX55', 'SN-RTR772Q'),
(6, 'Monitor', 'HP', 'ProDisplay P24', 'SN-MNT882C'),
(9, 'Keyboard', 'Logitech', 'MX Keys', 'SN-KBD114Z');

INSERT INTO SoftwareLicense (S_assetID, softwareName, version, licenseKey, numOfUsers) VALUES
(2, 'Microsoft Office 365', 'Business Standard', 'O365-92KD-XY33-HF21', 25),
(4, 'Adobe Photoshop', '2024', 'ADBP-88DH-223J-KL99', 2),
(7, 'FortiClient VPN', '7.2', 'FCTVN-12KD-77HS-PLQ8', 50),
(8, 'Windows 11 Pro', '23H2', 'WIN11P-91KD-QWEE-118Z', 1),
(10, 'AutoCAD LT', '2023', 'ACLT-55RH-992L-ZXT7', 1);

INSERT INTO Inventory (itemName, itemType, unit, stockQty, reorderLevel, i_status) VALUES
('Cat6 Ethernet Cable', 'Networking', 'pieces', 120, 30, 'active'),
('SSD 1TB Samsung 870 EVO', 'Storage', 'pieces', 25, 5, 'active'),
('Wireless Mouse Logitech M331', 'Peripheral', 'pieces', 60, 15, 'active'),
('A4 Bond Paper', 'Office Supply', 'reams', 40, 10, 'active'),
('RJ45 Connectors', 'Networking', 'pack', 15, 5, 'active'),
('Printer Ink HP 680 Black', 'Consumable', 'cartridge', 8, 3, 'active'),
('USB-C to HDMI Adapter', 'Accessory', 'pieces', 20, 5, 'active'),
('Laptop Stand Aluminum Adjustable', 'Accessory', 'pieces', 12, 3, 'inactive'),
('Anti-Static Wrist Strap', 'Tool', 'pieces', 50, 10, 'active'),
('External Hard Drive Seagate 2TB', 'Storage', 'pieces', 10, 2, 'active');

INSERT INTO Supplier (companyName, firstName, lastName, contactNumber, email, zip, street, city) VALUES
('TechCore Distributors', 'Mark', 'Villanueva', '09171234567', 'mark.v@techcore.com', '1105', 'Aurora Blvd.', 'Quezon City'),
('Prime Office Solutions', 'Anna', 'Lopez', '09285551234', 'anna.lopez@primeoffice.ph', '1200', 'EDSA Extension', 'Pasay'),
('Innovatech Hardware Supply', 'John', 'Reyes', '09998887711', 'john.reyes@innovatech.com', '8000', 'JP Laurel Ave.', 'Davao City'),
('Metro IT Parts', 'Christine', 'Tan', '09187654321', 'ctan@metroitsupply.com', '6000', 'Osmeña Blvd.', 'Cebu City'),
('BlueWave Software Licensing', 'Daniel', 'Santos', '09351239876', 'dsantos@bluewave.ph', '1605', 'Ortigas Ave.', 'Pasig'),
('Reliable Office Depot', 'Karen', 'Lim', '09081234567', 'k.lim@reliabledepot.com', '1008', 'España Blvd.', 'Manila'),
('Digital Edge Components', 'Robert', 'Chua', '09175550000', 'rchua@digitaledge.com', '5000', 'Rizal St.', 'Iloilo City'),
('Unified Network Supplies', 'Jessa', 'Martinez', '09951234567', 'jessa.m@unsupplies.ph', '4026', 'Santa Rosa Tagaytay Rd.', 'Santa Rosa'),
('SecureSoft Licensing Hub', 'Patrick', 'Gomez', '09273334444', 'pgomez@securesoft.com', '7000', 'Pablo Ocampo St.', 'Zamboanga City'),
('NorthPoint Computer Parts', 'Elaine', 'Castro', '09364442222', 'ecast@northpointparts.ph', '2400', 'MacArthur Highway', 'Tarlac City');

INSERT INTO Assigning (assignedBy, assetID, assignmentDate, as_status) VALUES
(1, 1, '2024-01-10', 'active'),       -- Assigned laptop
(2, 2, '2024-01-12', 'active'),       -- Assigned software license
(3, 4, '2024-01-20', 'inactive'),     -- Assignment ended
(4, 6, '2024-02-05', 'active'),       -- Hardware asset
(5, 8, '2024-02-18', 'active'),       -- Software
(6, 3, '2024-03-02', 'inactive'),     -- Expired hardware previously assigned
(7, 5, '2024-03-14', 'active'),       -- Damaged hardware tracked
(8, 7, '2024-03-28', 'inactive'),     -- Returned software
(9, 9, '2024-04-11', 'active'),       -- Lost hardware logged as assigned historically
(10, 10, '2024-04-25', 'active');     -- Inactive software license assigned

INSERT INTO Monitoring (assetID, monitoringType, lastCheckedDate, expiryDate, notes) VALUES
(1, 'hardware', '2024-05-15', NULL, 'Routine hardware inspection completed.'),
(2, 'software', '2024-05-20', '2026-11-22', 'License verification OK.'),
(3, 'hardware', '2024-06-01', NULL, 'Unit showing signs of wear; monitor closely.'),
(4, 'software', '2024-06-10', '2028-02-18', 'Auto-renewal enabled.'),
(5, 'hardware', '2024-06-18', NULL, 'Damaged unit; awaiting replacement approval.'),
(6, 'hardware', '2024-07-02', NULL, 'Operating normally, no issues.'),
(7, 'software', '2024-07-15', '2025-04-05', 'Marked as returned; license no longer in use.'),
(8, 'software', '2024-07-22', '2029-01-10', 'Software functioning properly.'),
(9, 'hardware', '2024-08-05', NULL, 'Item reported lost; monitoring archived.'),
(10, 'software', '2024-08-18', '2026-03-25', 'Inactive license, last audit passed.');

INSERT INTO SoftwareMonitoring (S_monitoringID, depreciationStatus) VALUES
(2, 'Good – License fully valid'),
(4, 'Good – Within subscription period'),
(7, 'Deactivated – Returned license'),
(8, 'Optimal – Long-term license active'),
(10, 'Inactive – License nearing renewal');

INSERT INTO HardwareMonitoring (H_monitoringID, complianceStatus) VALUES
(1, 'Compliant – No issues detected'),
(3, 'Minor issues – Wear observed'),
(5, 'Non-compliant – Damaged unit'),
(6, 'Compliant – Operating normally'),
(9, 'Non-compliant – Reported lost');

INSERT INTO Contract (assetID, supplierID, startDate, expiryDate, payment, responsibilities) VALUES
(1, 3, '2024-01-10', '2027-01-10', '₱48,000 – One-time', 'Supplier provides hardware diagnostics and annual maintenance.'),
(2, 5, '2024-02-05', '2026-02-05', '₱14,000/year', 'Supplier responsible for license renewal and version upgrades.'),
(3, 9, '2023-03-15', '2025-03-15', '₱25,000 – One-time', 'Supplier handles evaluation of expired hardware components.'),
(4, 2, '2024-03-20', '2028-03-20', '₱20,000/year', 'Supplier must handle software patching and technical support.'),
(5, 10, '2022-06-01', '2024-06-01', '₱18,000 – One-time', 'Supplier responsible for reviewing damage and providing replacement options.'),
(6, 1, '2023-07-18', '2027-07-18', '₱52,000 – One-time', 'Supplier provides compliance checks and annual servicing.'),
(7, 8, '2022-04-12', '2025-04-12', '₱12,000/year', 'Supplier manages license deactivation and reassignment processes.'),
(8, 4, '2024-01-28', '2029-01-28', '₱24,000/year', 'Supplier ensures long-term subscription stability and cloud sync support.'),
(9, 7, '2021-09-05', '2024-09-05', '₱20,500 – One-time', 'Supplier handles lost equipment reporting and verification.'),
(10, 6, '2023-04-02', '2026-04-02', '₱16,000/year', 'Supplier responsible for license compliance and reactivation assistance.');

INSERT INTO RenewalContract 
(assetID, contractID, renewedBy, renewedFrom, renewalDate, newExpiryDate) VALUES
(1, 1, 3, 3, '2026-12-15', '2029-12-15'),
(2, 2, 2, 5, '2025-12-10', '2028-12-10'),
(3, 3, 9, 9, '2025-02-20', '2027-02-20'),
(4, 4, 4, 2, '2027-12-05', '2030-12-05'),
(5, 5, 8, 10, '2024-05-15', '2026-05-15'),
(6, 6, 1, 1, '2027-06-20', '2029-06-20'),
(7, 7, 7, 8, '2025-03-25', '2027-03-25'),
(8, 8, 5, 4, '2029-01-05', '2031-01-05'),
(9, 9, 10, 7, '2024-08-10', '2026-08-10'),
(10, 10, 6, 6, '2026-03-15', '2028-03-15');

INSERT INTO Restock (supplierID, itemID, restockedBy, qtyReceived, unitCost, deliveryDate, orderDate) VALUES
(1, 1, 3, 85, 145.00, '2025-01-12', '2025-01-07'),   
(4, 2, 5, 30, 4450.00, '2025-02-18', '2025-02-12'),  
(3, 3, 2, 55, 360.00, '2025-03-04', '2025-02-28'),    
(2, 4, 6, 25, 115.00, '2025-03-22', '2025-03-18'),    
(5, 5, 1, 180, 28.00, '2025-04-03', '2025-03-30'),    
(6, 6, 9, 12, 1980.00, '2025-04-11', '2025-04-07'),  
(7, 7, 7, 20, 740.00, '2025-05-15', '2025-05-10'),    
(8, 8, 8, 6, 3480.00, '2025-05-28', '2025-05-23'),   
(9, 9, 4, 45, 125.00, '2025-06-03', '2025-05-29'),    
(10, 10, 10, 10, 7600.00, '2025-06-17', '2025-06-12');