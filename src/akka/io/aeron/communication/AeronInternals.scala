package akka.io.aeron.communication

import uk.co.real_logic.aeron.driver.MediaDriver
import uk.co.real_logic.aeron.driver.ThreadingMode
import uk.co.real_logic.aeron.common.BackoffIdleStrategy
import uk.co.real_logic.aeron.common.BusySpinIdleStrategy

class AeronInternals {
 
  
  val ctx = new MediaDriver.Context()
            .threadingMode(ThreadingMode.DEDICATED)
            .conductorIdleStrategy(new BackoffIdleStrategy(1, 1, 1, 1))
            .sharedNetworkIdleStrategy(new BackoffIdleStrategy(1,1,1,1))
            .sharedIdleStrategy(new BackoffIdleStrategy(1,1,1,1))
            .receiverIdleStrategy(new BackoffIdleStrategy(1,1,1,1))
            .senderIdleStrategy(new BackoffIdleStrategy(1,1,1,1));

  val driver = MediaDriver.launch(ctx)
  

}
