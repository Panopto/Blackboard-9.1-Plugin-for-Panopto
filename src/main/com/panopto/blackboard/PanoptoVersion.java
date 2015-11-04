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

import com.panopto.services.IAuth;


/**
 * Internal class for determining compatibility using Panopto api version
 */
class PanoptoVersion implements Comparable<PanoptoVersion> {

    static final PanoptoVersion EMPTY = new PanoptoVersion(new int[0]);

    static final PanoptoVersion V4_9 = PanoptoVersion.from("4.9");

    static PanoptoVersion from(String versionString){
        PanoptoVersion toReturn;
        if(versionString != null){
            try {
                final String[] stringParts = versionString.split("\\.");
                final int[] parts = new int[stringParts.length];

                for (int i = 0; i < stringParts.length; i++) {
                    parts[i] = Integer.valueOf(stringParts[i]);
                }
                toReturn = new PanoptoVersion(parts);
            } catch (NumberFormatException e) {
                toReturn = EMPTY;
            }
        }else{
            toReturn = EMPTY;
        }

        return toReturn;
    }

    static PanoptoVersion fetchOrEmpty(IAuth iAuth) {
        PanoptoVersion serverVersion;
        try {
            serverVersion = PanoptoVersion.from(iAuth.getServerVersion());
        } catch (Exception e) {
            Utils.log(e, "Error retrieving Panopto server version from the server.");
            serverVersion= EMPTY;
        }
        return serverVersion;
    }

    private int[] parts;

    private PanoptoVersion(int[] parts) {
        this.parts = parts;
    }

    public int getNumParts(){
        return parts.length;
    }

    public int getPart(int i){
        return parts[i];
    }

    //Determines whether or not the block is able to call session or folder availability window API methods
    //based on the version of the current Panopto server.
    //Availability window api functions were introduced in server version 4.9.0,
    //If the current server version is less than 4.9.0.00000, Availability Window API should not be called.
    public boolean canCallAvailabilityWindowApiMethods(){
        return this.compareTo(V4_9) >= 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if(i > 0){
                stringBuilder.append(".");
            }
            stringBuilder.append(parts[i]);
        }
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(PanoptoVersion other) {
        int compare = 0;

        for (int i = 0; compare == 0 && i < Math.min(this.parts.length, other.parts.length); i++) {
            compare = Integer.compare(this.parts[i], other.parts[i]);
        }
        boolean stillEqualButDifferentLengths = compare == 0 && this.parts.length != other.parts.length;
        if(stillEqualButDifferentLengths){
            //then whoever has longer version is after
            compare = Integer.compare(this.parts.length, other.parts.length);
        }
        return compare;
    }
}
