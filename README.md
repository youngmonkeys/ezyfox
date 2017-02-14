# ezyfox
## ezyfox-core

[![Build Status](https://travis-ci.org/youngmonkeys/ezyfox-core.svg?branch=master)](https://travis-ci.org/youngmonkeys/ezyfox-core)
[![Dependency Status](https://www.versioneye.com/user/projects/5749e142138081000ea4f078/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5749e142138081000ea4f078)
[![Coverage Status](https://coveralls.io/repos/github/youngmonkeys/ezyfox-core/badge.svg?branch=master)](https://coveralls.io/github/youngmonkeys/ezyfox-core?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.tvd12/ezyfox-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.tvd12/ezyfox-core)
[![Javadoc](https://javadoc-emblem.rhcloud.com/doc/com.tvd12/ezyfox-core/badge.svg)](http://www.javadoc.io/doc/com.tvd12/ezyfox-core)

## ezyfox-sfs2x

[![Build Status](https://travis-ci.org/youngmonkeys/ezyfox-sfs2x.svg?branch=master)](https://travis-ci.org/youngmonkeys/ezyfox-sfs2x)
[![Dependency Status](https://www.versioneye.com/user/projects/574d34c8193d44000d50dd82/badge.svg?style=flat)](https://www.versioneye.com/user/projects/574d34c8193d44000d50dd82)
[![Coverage Status](https://coveralls.io/repos/github/youngmonkeys/ezyfox-sfs2x/badge.svg?branch=master)](https://coveralls.io/github/youngmonkeys/ezyfox-sfs2x?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.tvd12/ezyfox-sfs2x/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.tvd12/ezyfox-sfs2x)
[![Javadoc](https://javadoc-emblem.rhcloud.com/doc/com.tvd12/ezyfox-sfs2x/badge.svg)](http://www.javadoc.io/doc/com.tvd12/ezyfox-sfs2x)

#Synopsis

This project supports to create a server side game application more efficiently and more quickly (use smartfox server engine)

#Code Example

**1. Handle server ready event**
```java
  @ServerEventHandler(event = ServerEvent.SERVER_READY)
  public class ServerReadyHandler {
    
    public void handle(AppContext context) {
        context.command(CreateRoom.class)
            .agents(RoomProvider.lobby()).execute();
        MyRoom[] rooms = RoomProvider.gameRooms(); 
        context.command(CreateRoom.class).agents(rooms).execute();
        for(MyRoom room : rooms) {
            context.command(UpdateRoom.class)
                .toClient(true).room(room).execute();
        }
    }
}
```

**2. Handle user join zone event**
```java
  @ServerEventHandler(event = ServerEvent.USER_JOIN_ZONE)
  public class UserJoinZoneHandler {
    public void handle(AppContext context, ApiZone zone, MyUser user) {
        context.command(Log.class).from(this).info("user " + user.getName() 
              + " join zone " + zone.getName());
        context.command(JoinRoom.class)
            .roomToJoin("Lobby").user(user).execute();
    }
  }
```

**3. Handle user join room event**

```java
  @RoomName("v")
  @ServerEventHandler(event = ServerEvent.USER_JOIN_ROOM)
  public class UserJoinRoomHandler {
    public void handle(AppContext context, MyRoom room, MyUser user) {
        context.command(Log.class).from(this).info("user " + user.getName() 
              + " joined room " + room.getName());
        context.command(Response.class)
            .command("1").recipients(user).data(room).execute();
    }
    
}
```

**4. Listen a request from client and auto response**

Let's say you want to listen a "bet" request from client and you also you want to response to that client, you can do like this:

```java
  @Data
  @ClientResponseHandler // use this annotation to auto response to client
  @ClientRequestListener(command = "2") // use this annotation to listen a request from client
  public class BetRequestListener {
    @RequestParam("1")
    @ResponseParam("1")
    private int money;
    
    @RequestParam("2")
    @ResponseParam("2")
    private int roomId;
    
    public void execute(AppContext context, MyUser user) {
        MyRoom room = 
                context.command(FindRoom.class).by(roomId);
        context.command(Log.class).from(this).info("user {} has just betted {} chips", 
                user.getName(), btype.getMoney());
        user.decreaseMoney(btype.getMoney());
        user.increaseGameMoney(btype.getMoney());
        context.command(UpdateUser.class)
            .toClient(true).user(user).execute();
        context.command(UpdateRoom.class)
            .room(room).toClient(true).execute();
    }
    
}

```

**5. Intercept a request from client**

Let's say you need validate a parameter in a request from client (i.e money in "bet" request) you can do:

```java
  @Data
  @ClientRequestListener(command = "2", priority = -1)
  public class BetRequestInterceptor {
    
    @RequestParam("1")
    @ResponseParam("1")
    private int money;
    
    @RequestParam("2")
    @ResponseParam("2")
    private int roomId;
    
    public void execute(AppContext context, MyUser user) throws Exception {
        if(money <= 0) {
            context.command(Response.class)
                .command("2").recipients(user).execute();
            throw new BadRequestException();
        }
    }
    
}
```

#Motivation

We have used smartfox server engine, we must use key-value object like SFSObject, SFSUser, SFSRoom e.t.c, they make our source code too complex, hard to read, hard to map, example:

```java
  // public user var
  UserVariable avatarPic = new SFSUserVariable("pic", "GonzoTheGreat.jpg");
   
  // private user var
  UserVariable dbId = new SFSUserVariable("dbId", databaseId);
  dbId.setHidden(true);
     
  // Set variables via the server side API
  getApi().setUserVariables(user, Arrays.asList(avatarPic, dbId));
```

We must spend a lot of time to declare, init variables. Some time we also have two or three user agent for each game, and mapping an user agent to key-value object is hard. We think using POJO is good idea, so we make this project.

#Installation

```xml
	<dependency>
		<groupId>com.tvd12</groupId>
		<artifactId>ezyfox-core</artifactId>
		<version>1.2.6</version>
	</dependency>
	<dependency>
		<groupId>com.tvd12</groupId>
		<artifactId>ezyfox-sfs2x</artifactId>
		<version>1.2.6</version>
	</dependency>
```

#API Reference

http://www.javadoc.io/doc/com.tvd12/ezyfox-core

#Tests

mvn test

#Contributors

- Project management 
  - [NamCV](mailto:cungvinhnam@gmail.com)
- Project development
 - [DungTV](mailto:dungtv192@gmail.com)
- Project documentation
 - [DungTV](mailto:dungtv192@gmail.com)
 - [DatNT](mailto:dat.fithou@gmail.com)

#License

- Apache License, Version 2.0
