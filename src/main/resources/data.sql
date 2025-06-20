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

---- USERS
--INSERT INTO user (id, name, email, is_organizer) VALUES
--(1, 'Alice Organizer', 'alice@events.com', true),
--(2, 'Bob Attendee', 'bob@mail.com', false),
--(3, 'Charlie Attendee', 'charlie@mail.com', false);
--
---- VENUES
--INSERT INTO venue (id, name, address, capacity) VALUES
--(1, 'Downtown Hall', '123 Main St', 100),
--(2, 'Tech Convention Center', '456 Innovation Blvd', 300),
--(3, 'The Loft', '789 High St', 50);
--
---- EVENTS
--INSERT INTO event (id, name, date, description, admission_price, capacity, venue_id, organizer_id) VALUES
--(1, 'Java Dev Meetup', '2025-07-01', 'A gathering of Java devs to share tips and pizza.', 10.00, 100, 1, 1),
--(2, 'Tech Startups Expo', '2025-07-05', 'Booths, pitches, and networking with local startups.', 20.00, 300, 2, 1),
--(3, 'Indie Game Night', '2025-07-10', 'Try out locally made video games and chat with devs.', 5.00, 50, 3, 1);
--
---- REGISTRATIONS
--INSERT INTO registration (id, user_id, event_id) VALUES
--(1, 2, 1),  -- Bob registered for Java Dev Meetup
--(2, 3, 1),  -- Charlie registered for Java Dev Meetup
--(3, 2, 2);  -- Bob registered for Tech Startups Expo

