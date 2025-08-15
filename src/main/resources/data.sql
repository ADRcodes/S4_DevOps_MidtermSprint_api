-- USERS
INSERT INTO `user` (id, name, email) VALUES
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

-- VENUES
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

-- EVENTS
-- (No tag column here; tags go into event_tags)
INSERT INTO event (id, company, title, date, description, price, capacity, venue_id, organizer_id) VALUES
  (1,  'TechCorp',     'DevOps Deeeeeep Dive',       '2025-07-15 09:30:00', 'CI/CD, IaC & monitoring workshop.',      149.99, 50,  1,  1),
  (2,  'GreenSoft',    'Java Performance Tuning','2025-08-01 13:00:00', 'Hands-on Java GC & profiling session.',   99.50, 30,  2,  2),
  (3,  'DataPros',     'AI in Production',       '2025-09-10 10:00:00', 'Deploying ML at scale.',                  199.00, 40,  3,  3),
  (4,  'CodeNest',     'Frontend Future Summit', '2025-10-05 11:00:00', 'React, Svelte & UX trends.',               89.00, 100, 4,  2),
  (5,  'CloudForge',   'Serverless Bootcamp',    '2025-11-02 09:00:00', 'Learn Lambda, API Gateway & more.',      129.00, 60,  5,  3),
  (6,  'PostgrePower', 'Advanced SQL & Indexing','2025-09-25 14:00:00', 'Performance tuning for relational DBs.', 119.00, 45,  6,  4),
  (7,  'NodeNova',     'Asynchronous Patterns',  '2025-10-12 16:00:00', 'Master promises, async/await, and more.',  75.00, 80,  7,  5),
  (8,  'StyleScript',  'CSS Beyond Basics',      '2025-10-19 10:00:00', 'Advanced layout techniques & design.',     65.00, 70,  8,  6),
  (9,  'BinaryBase',   'Low-Level Thinking',     '2025-11-04 09:30:00', 'Bits, bytes & performance hacks.',        105.00, 40,  9,  7),
  (10, 'ReactRoute',   'Routing Mastery',        '2025-12-01 13:00:00', 'Deep dive into SPAs and routers.',         95.00, 50, 10,  8),
  (11, 'LambdaWorks',  'Serverless Showcase',    '2025-12-12 09:00:00', 'Real-world use cases for serverless.',    140.00, 60, 11,  9),
  (12, 'StartupSpark', 'Pitch Fest 2025',        '2025-06-26 14:00:00', 'Local startups pitch to VCs.',             25.00, 120, 2,  5),
  (13, 'DevDocks',     'Container Day',          '2025-06-28 10:00:00', 'Docker & Kubernetes hands-on.',            99.00, 80, 10,  4),
  (14, 'DataMind',     'SQL Power Hour',         '2025-07-05 09:30:00', 'Advanced joins and indexes.',              59.00, 60,  6,  3),
  (15, 'LambdaWorks',  'Fast Functions',         '2025-07-20 13:00:00', 'Speed tuning serverless code.',            79.00, 90, 11,  6),
  (16, 'TechTalks',    'AI Ethics in Tech',      '2025-08-15 11:00:00', 'Discussing the implications of AI.',       45.00, 100, 3,  7),
  (17, 'CodeCraft',    'Frontend Frameworks',    '2025-09-01 10:00:00', 'Comparing React, Vue & Angular.',          85.00, 70,  4,  8),
  (18, 'DataDive',     'Big Data Analytics',     '2025-09-20 14:00:00', 'Tools and techniques for big data.',      110.00, 50,  2,  9),
  (19, 'CloudConnect', 'Hybrid Cloud Solutions', '2025-10-10 09:30:00', 'Integrating on-prem and cloud.',          120.00, 80,  5, 10),
  (20, 'DevOpsDays',   'CI/CD Best Practices',   '2025-11-05 13:00:00', 'Optimizing your CI/CD pipeline.',          95.00, 60,  1, 11);

-- USERS' PREFERRED TAGS (ElementCollection)
INSERT INTO preferred_tags (user_id, tag) VALUES
  (1, 'DevOps'), (1, 'Java'), (1, 'AI'),
  (2, 'JavaScript'), (2, 'Frontend'), (2, 'UX'),
  (3, 'AI'), (3, 'Machine Learning'), (3, 'Data Science'),
  (4, 'Serverless'), (4, 'AWS'), (4, 'Cloud'),
  (5, 'Database'), (5, 'SQL'), (5, 'PostgreSQL'),
  (6, 'JavaScript'), (6, 'Node.js'), (6, 'React'),
  (7, 'CSS'), (7, 'Design'), (7, 'Frontend'),
  (8, 'Frontend'), (8, 'UX'), (8, 'Design'),
  (9, 'Systems'), (9, 'Performance'), (9, 'Low-Level'),
  (10, 'AI'), (10, 'Ethics'), (10, 'Machine Learning'),
  (11, 'Serverless'), (11, 'AWS'), (11, 'Performance');

-- EVENT TAGS (ElementCollection)
INSERT INTO event_tags (event_id, tag) VALUES
  (1, 'DevOps'),
  (2, 'Java'),
  (3, 'AI'), (3, 'Machine Learning'),
  (4, 'Frontend'), (4, 'UX'),
  (5, 'Serverless'), (5, 'AWS'),
  (6, 'Database'), (6, 'SQL'),
  (7, 'JavaScript'), (7, 'Node.js'),
  (8, 'CSS'), (8, 'Design'),
  (9, 'Systems'), (9, 'Performance'),
  (10, 'React'), (10, 'SPA'),
  (11, 'Serverless'), (11, 'AWS'),
  (12, 'Startup'), (12, 'Pitching'),
  (13, 'Containers'), (13, 'Docker'),
  (14, 'Database'), (14, 'SQL'),
  (15, 'Serverless'), (15, 'Performance'),
  (16, 'AI'), (16, 'Ethics'),
  (17, 'Frontend'), (17, 'Frameworks'),
  (18, 'Big Data'),
  (19, 'Cloud'),
  (20, 'DevOps');

-- REGISTRATIONS
INSERT INTO registration (id, user_id, event_id, registration_date) VALUES
  (1, 1, 1,  '2025-06-25 09:00:00'),
  (2, 2, 1,  '2025-06-25 09:05:00'),
  (3, 3, 2,  '2025-06-25 09:10:00'),
  (4, 1, 3,  '2025-06-25 09:15:00'),
  (5, 4, 4,  '2025-06-25 09:20:00'),
  (6, 5, 5,  '2025-06-25 09:25:00'),
  (7, 6, 3,  '2025-06-25 09:30:00'),
  (8, 7, 1,  '2025-06-25 09:35:00'),
  (9, 6, 4,  '2025-06-25 09:40:00'),
  (10,8, 8,  '2025-06-25 09:45:00'),
  (11,9, 6,  '2025-06-25 09:50:00');
