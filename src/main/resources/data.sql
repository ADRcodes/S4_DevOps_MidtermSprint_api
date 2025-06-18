-- USERS
INSERT INTO user (id, name, email, is_organizer) VALUES
(1, 'Alice Organizer', 'alice@events.com', true),
(2, 'Bob Attendee', 'bob@mail.com', false),
(3, 'Charlie Attendee', 'charlie@mail.com', false);

-- VENUES
INSERT INTO venue (id, name, address, capacity) VALUES
(1, 'Downtown Hall', '123 Main St', 100),
(2, 'Tech Convention Center', '456 Innovation Blvd', 300),
(3, 'The Loft', '789 High St', 50);

-- EVENTS
INSERT INTO event (id, name, date, description, admission_price, capacity, venue_id, organizer_id) VALUES
(1, 'Java Dev Meetup', '2025-07-01', 'A gathering of Java devs to share tips and pizza.', 10.00, 100, 1, 1),
(2, 'Tech Startups Expo', '2025-07-05', 'Booths, pitches, and networking with local startups.', 20.00, 300, 2, 1),
(3, 'Indie Game Night', '2025-07-10', 'Try out locally made video games and chat with devs.', 5.00, 50, 3, 1);

-- REGISTRATIONS
INSERT INTO registration (id, user_id, event_id) VALUES
(1, 2, 1),  -- Bob registered for Java Dev Meetup
(2, 3, 1),  -- Charlie registered for Java Dev Meetup
(3, 2, 2);  -- Bob registered for Tech Startups Expo
