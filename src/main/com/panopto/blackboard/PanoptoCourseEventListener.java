/* Copyright Panopto 2009 - 2016
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

import blackboard.admin.persist.course.CloneConfig;
import blackboard.admin.persist.course.CourseEventListener;
import blackboard.data.course.CourseManager;
import blackboard.data.course.CourseManagerFactory;
import blackboard.persist.Id;

import com.panopto.blackboard.PanoptoData;
import com.panopto.blackboard.Utils;

/**
 * @author Panopto
 *
 *         Panopto implementation of CourseEventListener, this when registered with bb-manifest.xml allows the Panopto
 *         Blackboard building block to listen into course events. This listens into the courseCloned event so that we
 *         can copy over Panopto permissions.
 */
public class PanoptoCourseEventListener implements CourseEventListener {

    /*
     * Handle a course clone event so that if the source course is provisioned with Panopto we grant the target course
     * viewer access to the folders that the source course had access to. Only copies if the setting is enabled.
     * 
     * (non-Javadoc)
     * 
     * @see blackboard.admin.persist.course.CourseEventListener#courseCloned( blackboard.persist.Id,
     * blackboard.persist.Id, blackboard.admin.persist.course.CloneConfig)
     */
    public void courseCloned(Id sourceId, Id targetId, CloneConfig config) throws Exception {

        String userName = config.getLoggedOnUser();
        CourseManager manager = CourseManagerFactory.getInstance();
        PanoptoData targetCourse = new PanoptoData(manager.getCourse(targetId), userName);
        
        // Copy over permissions if the course copy setting is enabled for the site.
        if (Utils.pluginSettings.getCourseCopyEnabled()) {
            // Get the target course so we can copy into it from the source.
            targetCourse.copyCoursePermissions(manager.getCourse(sourceId));

            Utils.log(String.format("Course Cloned. Source ID: %s Target ID: %s", sourceId.toExternalString(),
                    targetId.toExternalString()));
        } else {
        	// We only set the context if it is not already set, see inside the function.
            targetCourse.setOriginalCopyContext(sourceId.toExternalString());
        }
    }

    /*
     * Stub implementation could be used in the future for automatic provisioning.
     * 
     * (non-Javadoc)
     * 
     * @see blackboard.admin.persist.course.CourseEventListener#courseCreated(blackboard.persist.Id)
     */
    public void courseCreated(Id courseId) {
        // Stub to fill out
        // System.out.println("Course Created. Course ID: " + courseId.toExternalString());
    }
}
