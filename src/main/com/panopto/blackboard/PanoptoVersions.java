/* Copyright Panopto 2009 - 2015
 *
 * This file is part of the Panopto plugin for Blackboard.
 *
 * The Panopto plugin for Blackboard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Panopto plugin for Blackboard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Panopto plugin for Blackboard.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.panopto.blackboard;

/**
 * Internal class for determining compatibility using Panopto api version
 */
class PanoptoVersions {

    static final PanoptoVersion V4_9 = PanoptoVersion.from("4.9");
    static final PanoptoVersion V5_3 = PanoptoVersion.from("5.3");

    //Determines whether or not the block is able to call session or folder availability window API methods
    //based on the version of the current Panopto server.
    //Availability window api functions were introduced in server version 4.9.0,
    //If the current server version is less than 4.9.0.00000, Availability Window API should not be called.
    static boolean canCallAvailabilityWindowApiMethods(PanoptoVersion panoptoVersion) {
        return panoptoVersion.compareTo(V4_9) >= 0;
    }
    
    
    /**
     * Determine if the panopto server version supports Copy methods (5.3 and above)
     * @param panoptoVersion Version of Panopto we are pointed at
     * @return boolean true if we have at least the minimum version number
     */
    static boolean canCallCopyApiMethods(PanoptoVersion panoptoVersion) {
        return panoptoVersion.compareTo(V5_3) >= 0;
    }

    /**
     * Not intended to be constructed.
     */
    private PanoptoVersions() {
    }

}
