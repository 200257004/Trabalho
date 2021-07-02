package pt.ips.tizito.messages;

public final class Info {

//	UserDAO
	public static final String USER_PERSIST = "Persisting a new user. Email: %s";
	public static final String USER_FIND_BY_ID = "Finding an user. Id: %s";
	public static final String USER_FIND_BY_EMAIL = "Finding an user. Email: %s";
	public static final String USER_REMOVE = "Removing an user. Email: %s";
	public static final String USER_LIST = "Listing users. Name: %s";
	public static final String USER_LIST_ALL = "Listing users.";

//	CategoryDAO
	public static final String CATEGORY_PERSIST = "Persisting a new category. Description: %s";
	public static final String CATEGORY_UPDATE = "Updating a category. Id: %s";
	public static final String CATEGORY_FIND_BY_ID = "Finding a category. Id: %s";
	public static final String CATEGORY_LIST = "Listing categories.";
	public static final String CATEGORY_REMOVE = "Removing a category. Id: %s";

//	IdeaDAO
	public static final String IDEA_FIND_BY_ID = "Finding an idea. Id: %s";

//	AuditableInterceptor
	public static final String AUDITABLE_INTERCEPTOR = "User: %s; Datetime: %s; Class: %s; Method: %s;";
	
//	MeetingDAO
	public static final String MEETING_PERSIST = "Persisting a new meeting.";
	public static final String MEETING_FIND_BY_ID = "Finding a meeting. Id: %s";
	public static final Object MEETING_LIST = "Listing meetings. User: %s";
	
//	InviteDAO
	public static final String INVITE_FIND_BY_ID = "Finding a invite. Id: %s";
	public static final Object INVITE_LIST = "Listing invites. User: %s";
	
//	RequestDAO
	public static final String REQUEST_PERSIST = "Persisting a new request.";
	public static final String REQUEST_FIND_BY_ID = "Finding a request. Id: %s";
	public static final String REQUEST_REMOVE = "Removing a request. Id: %s";
	public static final String REQUEST_LIST = "Listing requests. User: %s";
	public static final String REQUEST_FIND_BY_REQUESTER_AND_REQUESTED = "Finding a request.";

}
