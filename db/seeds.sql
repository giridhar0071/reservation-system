INSERT INTO patient (email, first_name, last_name, dob, phone) VALUES
  ('aarav.sharma@example.com',   'Aarav',    'Sharma',   '1990-04-12', '9123456780'),
  ('saanvi.patel@example.com',   'Saanvi',   'Patel',    '1987-11-23', '9234567891'),
  ('rohan.singh@example.com',     'Rohan',    'Singh',    '1992-07-05', '9345678902'),
  ('priya.kumar@example.com',     'Priya',    'Kumar',    '1993-02-17', '9456789013'),
  ('vikram.rao@example.com',      'Vikram',   'Rao',      '1985-09-30', '9567890124'),
  ('ananya.gupta@example.com',    'Ananya',   'Gupta',    '1994-12-01', '9678901235'),
  ('rahul.mehta@example.com',     'Rahul',    'Mehta',    '1989-06-18', '9789012346'),
  ('kavita.joshi@example.com',    'Kavita',   'Joshi',    '1991-08-25', '9890123457'),
  ('amit.khanna@example.com',     'Amit',     'Khanna',   '1986-10-10', '9965432108'),
  ('neha.iyer@example.com',       'Neha',     'Iyer',     '1995-03-03', '9876501234');

INSERT INTO representative (email, first_name, last_name, dob, phone) VALUES
  ('ravi.sharma@org.com',     'Ravi',     'Sharma',   '1982-05-14', '9123405678'),
  ('deepika.patel@org.com',   'Deepika',  'Patel',    '1987-09-22', '9234516789'),
  ('vikas.singh@org.com',      'Vikas',    'Singh',    '1990-11-03', '9345627890'),
  ('anita.kumar@org.com',      'Anita',    'Kumar',    '1985-02-18', '9456738901'),
  ('mano.rrao@org.com',        'Manoj',    'Rao',      '1978-07-30', '9567849012'),
  ('shruti.gupta@org.com',     'Shruti',   'Gupta',    '1992-12-11', '9678950123'),
  ('sachin.mehta@org.com',     'Sachin',   'Mehta',    '1989-04-27', '9789061234'),
  ('neetu.joshi@org.com',      'Neetu',    'Joshi',    '1991-08-05', '9890172345'),
  ('amit.khanna@org.com',      'Amit',     'Khanna',   '1983-10-16', '9965283456'),
  ('preeti.iyer@org.com',      'Preeti',   'Iyer',     '1994-03-09', '9876394567');


INSERT INTO organization (name) VALUES
  ('Apollo Hospital'),
  ('Fortis Healthcare'),
  ('Max Care Hospital'),
  ('Shalby Hospital'),
  ('Narayana Health');


INSERT INTO postal_code (zip, city, state, lat, lon) VALUES
  ('110001', 'New Delhi',   'Delhi',             28.6358, 77.2245),
  ('560001', 'Bengaluru',   'Karnataka',         12.9716, 77.5946),
  ('400001', 'Mumbai',      'Maharashtra',       18.9388, 72.8355),
  ('700001', 'Kolkata',     'West Bengal',       22.5726, 88.3639),
  ('600001', 'Chennai',     'Tamil Nadu',        13.0827, 80.2707),
  ('500001', 'Hyderabad',   'Telangana',         17.3850, 78.4867),
  ('380001', 'Ahmedabad',   'Gujarat',           23.0225, 72.5714),
  ('682001', 'Kochi',       'Kerala',             9.9312, 76.2673),
  ('753001', 'Bhubaneswar', 'Odisha',            20.2961, 85.8245),
  ('751001', 'Cuttack',     'Odisha',            20.4625, 85.8828);
