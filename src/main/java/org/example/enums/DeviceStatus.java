package org.example.enums;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum DeviceStatus {
    MOUSE_DISABLED {
        @Override
        public String msg() {
            return "m_disabled_" + getPcName();
        }
    },

    HEADPHONES_DISABLED {
        @Override
        public String msg() {
            return "h_disabled_" + getPcName();
        }
    },

    KEYBOARD_DISABLED {
        @Override
        public String msg() {
            return "k_disabled_" + getPcName();
        }
    };

    public abstract String msg();

    public String getPcName() {
        String computerName = null;
        try {
            computerName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return computerName;
    }
}
