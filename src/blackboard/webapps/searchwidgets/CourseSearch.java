/* Copyright Panopto 2009 - 2011
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
package blackboard.webapps.searchwidgets;

import blackboard.cms.util.DbBbObjectMapFilteringProxy;
import blackboard.cms.util.SimpleSelectQueryCS;
import blackboard.data.course.Course;
import blackboard.data.course.Course.ServiceLevel;
import blackboard.db.BbDatabase;
import blackboard.db.ConnectionNotAvailableException;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.Container;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.impl.CourseDbMap;
import blackboard.platform.persistence.PersistenceService;
import blackboard.platform.persistence.PersistenceServiceFactory;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseSearch
{
  public static final int SEARCH_BY_COURSE_NAME = 1;
  public static final int SEARCH_BY_COURSE_DESC = 2;
  public static final int SEARCH_BY_COURSE_ID = 3;
  public static final int MAX_ROWS = 10000;

  public static List<Course> searchForCourses(String searchString, boolean exactMatch, int searchBy, boolean isCourseSearch) throws PersistenceException
  {
    try
    {
      Set desiredAttributes = new HashSet();
      desiredAttributes.add("BatchUid");
      desiredAttributes.add("CourseId");
      desiredAttributes.add("DataSourceId");
      desiredAttributes.add("Description");
      desiredAttributes.add("id");
      desiredAttributes.add("IsAvailable");
      desiredAttributes.add("RowStatus");
      desiredAttributes.add("Title");
      desiredAttributes.add("ServiceLevelType");

      DbBbObjectMapFilteringProxy lwCourseMap = new DbBbObjectMapFilteringProxy(CourseDbMap.MAP, desiredAttributes);
      SimpleSelectQueryCS query = new SimpleSelectQueryCS(lwCourseMap);

      query.setMaxRows(MAX_ROWS);
      Container container = PersistenceServiceFactory.getInstance().getDbPersistenceManager().getContainer();
      BbDatabase bbDatabase = BbDatabase.getDefaultInstance();
      query.init(bbDatabase, container);
      String strName = null;

      if (searchBy == 1)
      {
        strName = "Title";
      }
      else if (searchBy == 2)
      {
        strName = "Description";
      }
      else if (searchBy == 3)
      {
        strName = "CourseId";
      }
      else {
        throw new RuntimeException("CourseSearch: Unrecognized searchBy field.");
      }

      if (exactMatch)
      {
        if (strName == "Description")
        {
          if ((searchString == null) || (searchString.equals("")))
          {
            query.addCaseInsensitiveLikeWhere(strName, " " + searchString);
          }
          else {
            query.addCaseInsensitiveLikeWhere(strName, searchString);
          }

        }
        else {
          query.addCaseInsensitiveWhere(strName, searchString);
        }

      }
      else {
        query.addCaseInsensitiveLikeWhere(strName, "%" + searchString + "%");
      }

      if (isCourseSearch)
      {
        query.addWhere("ServiceLevelType", Course.ServiceLevel.FULL);
      }
      else {
        query.addWhere("ServiceLevelType", Course.ServiceLevel.COMMUNITY);
      }
      query.addWhere("RowStatus", Integer.valueOf(0));
      query.addWhere("IsAvailable", Boolean.TRUE);

      if (strName == "Description")
      {
        query.addOrderBy("Title");
      }
      else {
        query.addOrderBy(strName);
      }

      query.executeQuery(null);

      return query.getResults();
    }
    catch (SQLException e)
    {
      throw new PersistenceException(e);
    }
    catch (ConnectionNotAvailableException e)
    {
      throw new PersistenceException(e);
    }
  }
}