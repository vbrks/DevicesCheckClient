package org.example.enums;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum EventMessages {
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
    },
    PC_NAME {
        @Override
        public String msg() {
            return getPcName();        }
    };

    public abstract String msg();

    public String getPcName() {
        String computerName = null;
        try {
            computerName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return computerName;
    }
}
