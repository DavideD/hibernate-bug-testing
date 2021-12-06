create table if not exists devices
(
    id          uuid primary key,
    name        varchar(255) not null,
    description text
);

create table if not exists features
(
    id           uuid primary key,
    name         varchar(255) not null,
    feature_type varchar(255) not null,
    device_id    uuid         not null references devices (id)
);

insert into devices(id, name, description)
VALUES ('69ea6d8c-0961-4341-a270-e8a17b29c890',
        'smart relay',
        'a relay that can be triggered via mqtt'),
       ('16eb07b9-cded-4538-b0dd-583f4083a2fa',
        'smart washer-machine',
        'start washer-machine from your office');

insert into features(id, name, feature_type, device_id)
VALUES ('0e7f0cc6-3d97-4829-b90e-5bae7976c21b',
        'TURN_ON',
        'SWITCH',
        '69ea6d8c-0961-4341-a270-e8a17b29c890'
       ),
       ('79f0d839-3e60-412d-b0e1-7c90ef57c0ad',
        'TURN_OFF',
        'SWITCH',
        '69ea6d8c-0961-4341-a270-e8a17b29c890'
       ),
       ('b2eee830-a946-4965-847a-adc363e1479d',
        'START',
        'WASH',
        '16eb07b9-cded-4538-b0dd-583f4083a2fa'
       ),
       ('06292075-c161-48cd-aa44-2132edd7da29',
        'STOP',
        'WASH',
        '16eb07b9-cded-4538-b0dd-583f4083a2fa'
       );