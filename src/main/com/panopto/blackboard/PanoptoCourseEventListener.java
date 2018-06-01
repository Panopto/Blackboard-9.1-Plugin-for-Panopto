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
import blackboard.data.course.Course;
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
        
        Course targetCourse = manager.getCourse(targetId);
        PanoptoData targetCourseData = new PanoptoData(targetCourse, userName);
        
        Course sourceCourse = manager.getCourse(sourceId);
        PanoptoData sourceCourseData = new PanoptoData(sourceCourse, userName);
        
        // Even if course copy is not enabled by Panopto blackboard will copy all data base registries including Panopto ones, so we need to clean them up.
        targetCourseData.handleCopyRegistryChanges(sourceCourseData);
        
        // Copy over permissions if the course copy setting is enabled for the site.
        if (Utils.pluginSettings.getCourseCopyEnabled()) {
            if (sourceCourseData.isMapped()) {
                if(!targetCourseData.isMapped()) {
                    Utils.log("Target course with Id (" + targetCourse.getCourseId() + ") was not provisioned, we are provisioning it to a default folder before handling the import!");
                    targetCourseData.provisionCourse(sourceCourseData.getServerName());
                }
                // Get the target course so we can copy into it from the source.
                targetCourseData.copyCoursePermissions(sourceCourse);
    
                Utils.log(String.format("Course Cloned. Source ID: %s Target ID: %s", sourceId.toExternalString(),
                        targetId.toExternalString()));
            } else {
                Utils.log("Source course with Id (" + sourceCourse.getCourseId() + ") was not provisioned, Blackboard course copy can continue, however Panopto course copy has no folders to copy.");
            }
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
