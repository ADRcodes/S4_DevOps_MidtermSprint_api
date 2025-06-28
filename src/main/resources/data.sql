-- Users
INSERT INTO user (id, name, email) VALUES
(1, 'Alice Smith', 'alice@example.com'),
(2, 'Bob Johnson', 'bob.johnson@example.com'),
(3, 'Carol Martinez', 'carol.m@example.com'),
(4, 'Alec Devlin', 'alec.dev@example.com'),
(5, 'Alec John', 'aj@techbyte.io'),
(6, 'Noah Lin', 'noah.lin@stackzone.dev'),
(7, 'Abdul Brown', 'abdul.b@cloudvault.tech'),
(8, 'Sofia Vega', 'sofia.v@frontendflow.io'),
(9, 'Liam Patel', 'liam.patel@dbforge.org'),
(10, 'Mina Zhou', 'mina.z@quantumquery.ai'),
(11, 'Jake Sanders', 'jsanders@devmail.io');

-- Venues
INSERT INTO venue (id, name, address, capacity) VALUES
(1, 'Downtown Conference Center', '123 Main St, Springfield', 200),
(2, 'Riverside Expo Hall', '456 River Rd, Shelbyville', 500),
(3, 'Tech Campus Auditorium', '789 Tech Blvd, Capital City', 300),
(4, 'Innovation Loft', '321 Startup Ln, Techville', 150),
(5, 'Skyline Labs', '987 Cloud Ave, Devton', 100),
(6, 'CodeHub Central', '654 Git Dr, Reposville', 220),
(7, 'Quantum Conference Hall', '111 Entangle Rd, Quark City', 180),
(8, 'AI Collaborative Space', '222 Neural Ln, Modeltown', 140),
(9, 'DevGrid Pavilion', '333 CSS Way, Flexville', 400),
(10, 'Docker Dock', '44 Container Blvd, Shipyard', 120),
(11, 'Lambda Lounge', '55 EventLoop Cir, Serverless City', 250);

-- Events
INSERT INTO event (id, company, title, date, description, price, capacity, venue_id, organizer_id) VALUES
(1, 'TechCorp', 'DevOps Deep Dive', '2025-07-15T09:30:00', 'CI/CD, IaC & monitoring workshop.', 149.99, 50, 1, 1),
(2, 'GreenSoft', 'Java Performance Tuning', '2025-08-01T13:00:00', 'Hands-on Java GC & profiling session.', 99.50, 30, 2, 2),
(3, 'DataPros', 'AI in Production', '2025-09-10T10:00:00', 'Deploying ML at scale.', 199.00, 40, 3, 3),
(4, 'CodeNest', 'Frontend Future Summit', '2025-10-05T11:00:00', 'React, Svelte & UX trends.', 89.00, 100, 4, 2),
(5, 'CloudForge', 'Serverless Bootcamp', '2025-11-02T09:00:00', 'Learn Lambda, API Gateway & more.', 129.00, 60, 5, 3),
(6, 'PostgrePower', 'Advanced SQL & Indexing', '2025-09-25T14:00:00', 'Performance tuning for relational DBs.', 119.00, 45, 6, 4),
(7, 'NodeNova', 'Asynchronous Patterns', '2025-10-12T16:00:00', 'Master promises, async/await, and more.', 75.00, 80, 7, 5),
(8, 'StyleScript', 'CSS Beyond Basics', '2025-10-19T10:00:00', 'Advanced layout techniques & design.', 65.00, 70, 8, 6),
(9, 'BinaryBase', 'Low-Level Thinking', '2025-11-04T09:30:00', 'Bits, bytes & performance hacks.', 105.00, 40, 9, 7),
(10, 'ReactRoute', 'Routing Mastery', '2025-12-01T13:00:00', 'Deep dive into SPAs and routers.', 95.00, 50, 10, 8),
(11, 'LambdaWorks', 'Serverless Showcase', '2025-12-12T09:00:00', 'Real-world use cases for serverless.', 140.00, 60, 11, 9),
(12, 'StartupSpark', 'Pitch Fest 2025', '2025-06-26T14:00:00', 'Local startups pitch to VCs.', 25.00, 120, 2, 5),
(13, 'DevDocks', 'Container Day', '2025-06-28T10:00:00', 'Docker & Kubernetes hands-on.', 99.00, 80, 10, 4),
(14, 'DataMind', 'SQL Power Hour', '2025-07-05T09:30:00', 'Advanced joins and indexes.', 59.00, 60, 6, 3),
(15, 'LambdaWorks', 'Fast Functions', '2025-07-20T13:00:00', 'Speed tuning serverless code.', 79.00, 90, 11, 6);


-- Registrations
INSERT INTO registration (id, user_id, event_id) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 2),
(4, 1, 3),
(5, 4, 4),
(6, 5, 5),
(7, 6, 3),
(8, 7, 1),
(9, 6, 4),
(10, 8, 8),
(11, 9, 6);
