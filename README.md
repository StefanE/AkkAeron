##The What
Basically enables Aeron to be used directly from your standard Akka actor system

##The Why
- Enable "default" Akka components to support low latency / high throughput in distributed systems

##The How
- is the architecture?

- can I use it?


##The Next steps
All my todo stuff


##Design considerations

Should their be one actor for each direction? or should it handle both ways?
Will properly be a setting for tuning

How should we handle Aeron builtin vs. external process? 

Tuning settings should in general not be introduced in the middle layer. This should be done by Aeron if possible.
The only time where this should be done, is in order to match Aeron with the development model.

###Defaults
Defaults goal is to enable the "simplest" setup which can be tuned afterwards
- Communication direction => Both
- Aeron network driver => Embedded
- Aeron driver strategy => BackOffIdleStrategy more to come...
- Aeron pooling numbers - adjusting based on load (some clever way)


##Decision guide on which communication scheme to use from AKKA
TODO....

###Assumptions
- Distributed system accross multiple servers
Following factors affect decision

###factors
Throughput (msgs/s)

Latency (how fast will a message go from sender to receiver)

Connections / subscribers

Openness / Lockin

Reliability is a must

###Types
- Remote Actors
- TCP
- UDP
- Aeron
- HTTP
- Streams?

 



