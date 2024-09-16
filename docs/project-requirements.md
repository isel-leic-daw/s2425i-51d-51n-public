# Project for the 2024/2025 Fall Semester

## Introduction

The goal of the project is the development of a multi-user Instant Messaging (IM) system.
This system will be composed by a _backend component_ and a _frontend application_.

The _backend component_ will be comprised by:
- A PostgreSQL Relational Database Management System (RDBMS), responsible for storing all the system's durable data, including the messages.
- A JVM-based application, responsible for:
    - Exposing an HTTP API, used by the _frontend application_, with all the functionality provided by the system.
    - Interacting with the RDBMS.
    - Ensuring all the domain requirements, namely data mutation integrity and access control.
- Other required sub-components, such as load balancers.

The _frontend application_ will run on the end user browsers and will be responsible for:
- Presenting a user interface.
- Interacting with the HTTP API exposed by the _backend component_, in order to implement all the functionalities of the system.

The system should also support _frontend applications_ running on different platforms, such as Android or iOS.

## Requirements

### Users and authentication

The system will be used by multiple users, requiring user authentication for most of the provided functionalities.
User authentication will be based on _usernames and passwords_. 
Authenticated session management will be based on the use of _authentication tokens_.
These tokens can be carried via the HTTP `Authorization` header or via cookies, depending on the type of _frontend application_.

User _registration_ will be based on one-time use invitations, created by other users.
There will be a way to bootstrap user registration, when no user was yet created.

### Channels and messages

The system will provide access to zero or more _channels_, where each _channel_ defines an ordered sequence of _messages_.
A channel will also have:
- An unique and stable identifier.
- A user visible name, which can change but must be unique.
- Access control rules.

A message is mainly defined by a text string, and should also be associated to:
- The user that created the message.
- The channel on which it is present (a message is present in one and only one channel).
- The creation timestamp for the message.

Users should be able to:
- Create channels.
- Join channels.
- View the latest messages in channels.
- Create messages in channels.

Channels can be _public_ or _private_.
The existence of _public_ channels is visible to all users.
In addition, any user can join or leave a public channel, as well as observe messages and create new messages.

_Private_ channels are only visible to the channel owner and to the users that were invited to the channel.
These invitations can be:
- _read-only_ - the invited user can observe the channel existence and can observe the channel's message, however they cannot create new messages in the channel.
- _read-write_ - the invited user can observe the channel existence, can observe the channel's message, and can create new messages in the channel.

### User interface

The user interface provided by the _frontend application_ should allow the following interactions:

* Registration of an user, given an invitation, a desired username and password.
* Authentication of an user, given an username and password.
* Generation of a user registration invitation.
* Visualization of all the joined channels.
* Visualization the messages in a joined channel.
* Posting a message into a joined channel.
* Searching for channels.
* Joining a channel.
* Leaving a channel.
* Creating a channel.

### Phased development and delivery

The project will be developed and delivered in two phases:
- The first phase delivery is the _backend component_, and should be delivered until 2024-10-19.
- The second phase delivery is the _frontend application_, and should be delivered until 2024-11-30. During the second phase changes and improvements can be made to the _backend component_.