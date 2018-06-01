/* Copyright Panopto 2009 - 2018
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

import com.panopto.blackboard.Utils;
import java.util.List;
import blackboard.data.course.Course;
import blackboard.data.course.Course.ServiceLevel;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.PersistenceException;
import blackboard.persist.SearchOperator;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseSearch;
import blackboard.persist.course.CourseSearch.SearchParameter;
import blackboard.platform.persistence.PersistenceServiceFactory;

// methods applicable outside the context of a particular Blackboard course used to retrieve Courses in the BB DB
public class PanoptoCourseSearch {
    public static final int SEARCH_BY_COURSE_NAME = 1;
    public static final int SEARCH_BY_COURSE_DESC = 2;
    public static final int SEARCH_BY_COURSE_ID = 3;
    public static final int MAX_ROWS = 200;


    public static List<Course> GetAllCourses() {
        BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();

        try {
            CourseDbLoader courseLoader = (CourseDbLoader) bbPm.getLoader(CourseDbLoader.TYPE);
            return courseLoader.loadAllCourses();
        } catch (Exception e) {
            Utils.log(e, "Error getting all courses.");
        }

        return null;
    }
    
    public static List<Course> searchForCourses(String searchString, boolean exactMatch, int searchBy, boolean isCourseSearch) {
        
        try {
            BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
            CourseDbLoader courseLoader = (CourseDbLoader) bbPm.getLoader(CourseDbLoader.TYPE);
            ServiceLevel targetServiceLevel = ServiceLevel.COMMUNITY;
            SearchOperator searchOp = SearchOperator.Contains;
            CourseSearch search = new CourseSearch();
            CourseSearch.SearchKey targetKey = CourseSearch.SearchKey.CourseName;
    
            if (searchBy == SEARCH_BY_COURSE_NAME) {
                targetKey = CourseSearch.SearchKey.CourseName;
            } else if (searchBy == SEARCH_BY_COURSE_DESC) {
                targetKey = CourseSearch.SearchKey.CourseDescription;
            } else if (searchBy == SEARCH_BY_COURSE_ID) {
                targetKey = CourseSearch.SearchKey.CourseId;
            } else {
                Utils.log("CourseSearch: Unrecognized searchBy field.");
            }
    
            if (exactMatch) {
                searchOp = SearchOperator.Equals;
            }
    
            if (isCourseSearch) {
                targetServiceLevel = ServiceLevel.FULL;
            }
            
            search = CourseSearch.getInfoSearch(targetKey, searchOp, searchString, targetServiceLevel);
            
            search.addParameter(new SearchParameter(targetKey, searchString, searchOp));
            search.setServiceLevel(targetServiceLevel);
            
            return courseLoader.loadByCourseSearch(search);
        } catch (Exception e ) {
            Utils.log(e, "Error getting courses through search.");
        }
        
        return null;
    }
}
