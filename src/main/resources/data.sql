-- ── Users ─────────────────────────────────────────────
INSERT INTO user (id, name, email) VALUES
  (1, 'Alice Smith',   'alice@example.com'),
  (2, 'Bob Johnson',   'bob.johnson@example.com'),
  (3, 'Carol Martinez','carol.m@example.com');

-- ── Venues ────────────────────────────────────────────
INSERT INTO venue (id, name, address, capacity) VALUES
  (1, 'Downtown Conference Center', '123 Main St, Springfield', 200),
  (2, 'Riverside Expo Hall',        '456 River Rd, Shelbyville', 500),
  (3, 'Tech Campus Auditorium',     '789 Tech Blvd, Capital City', 300);

-- ── Events ────────────────────────────────────────────
INSERT INTO event (id, company, title, date, description, price, capacity, venue_id, organizer_id) VALUES
  (1, 'TechCorp',    'DevOps Deep Dive',        '2025-07-15T09:30:00', 'CI/CD, IaC & monitoring workshop.',        149.99, 50, 1, 1),
  (2, 'GreenSoft',   'Java Performance Tuning', '2025-08-01T13:00:00', 'Hands-on Java GC & profiling session.',   99.50, 30, 2, 2),
  (3, 'DataPros',    'AI in Production',        '2025-09-10T10:00:00', 'Deploying ML at scale.',                  199.00, 40, 3, 3);

-- ── Registrations ─────────────────────────────────────
INSERT INTO registration (id, user_id, event_id) VALUES
  (1, 1, 1),
  (2, 2, 1),
  (3, 3, 2),
  (4, 1, 3);

