package com.wmh.member.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChannelUtils {
    @Value("${wmh.login.token.channel}")
    private String[] loginTokenChannel;

    public Boolean existChannel(String channel) {
        for (int i = 0; i < loginTokenChannel.length; i++) {
            if (channel.toLowerCase().equals(loginTokenChannel[i])) {
                return true;
            }
        }
        return false;
    }

}
