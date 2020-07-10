package net.bestdata.game.rocketgrab.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RequestListener {
    @RabbitListener(queues = "${queue.request.name}")
    public void receiveMessage(final RiotRequest riotRequest) {
        System.out.println(riotRequest);
    }
}
