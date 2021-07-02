package pt.ips.tizito.messages;

public final class Error {

//	User
	public static final String NAME_NOT_NULL = "Name must not be null.";
	public static final String NAME_SIZE = "Name size must be between %1$d and %2$d.";
	public static final String NAME_NOT_PATTERN = "Name must not match \"<script>\".";

	public static final String EMAIL_NOT_NULL = "Email must not be null.";
	public static final String EMAIL_SIZE = "Email size must be between 5 and %d.";
	public static final String EMAIL_PATTERN = "Email not a well-formed email address.";

	public static final String PASSWORD_NOT_NULL = "Password must not be null.";
	public static final String PASSWORD_SIZE = "Password size must be between %1$d and %2$d.";
	public static final String PASSWORD_PATTERN = "Password not a well-formed password.";

	public static final String FRIEND_NOT_NULL = "Friend must not be null.";

//	Category
	public static final String DESCRIPTION_NOT_NULL = "Description must not be null.";
	public static final String DESCRIPTION_SIZE = "Description size must be between %1$d and %2$d.";

	public static final String IDEIA_NOT_NULL = "Idea must not be null.";

//	AuthenticationFilter
	public static final String AUTHENTICATION_NOT_FOUND = "This request requires user authentication.";

//	AuthorizationInterceptor
	public static final String AUTHORIZATION_NOT_FOUND = "This request requires user authorization.";

//	AccountResource
	public static final String ACCOUNT_NOT_NULL = "Account must not be null.";

//	RequestResource
	public static final String REQUEST_NOT_NULL = "Request must not be null.";
	public static final String REQUESTED_NOT_NULL = "Requested must not be null.";
	public static final String REQUESTED_NOT_FOUND = "Requested not found.";
	public static final String REQUESTED_INATIVO = "Requested is not active.";
	public static final String REQUESTED_UNAUTHORIZED = "Requested is not authorized.";

//	CategoryResource
	public static final String CATEGORY_NOT_NULL = "Category must not be null.";
	public static final String CATEGORY_NOT_FOUND = "Category not found.";

//	Meeting
	public static final String INVITE_NOT_NULL = "Invite must not be null.";

//	MeetingResource
	public static final String MEETING_NOT_NULL = "Meeting must not be null.";
	public static final String MEETING_NOT_FOUND = "Meeting not found.";
	public static final String INVITEDS_NOT_NULL = "Inviteds must not be null.";
	public static final String INVITED_NOT_FOUND = "Invited not found.";
	public static final String INVITED_INACTIVE = "Invited is not active.";
	public static final String INVITED_UNAUTHORIZED = "Invited is not authorized.";
	public static final String LATITUDE_NOT_NULL = "Latitude must not be null.";
	public static final String LATITUDE_SIZE = "Latitude size must be between %1$d and %2$d.";
	public static final String LONGITUDE_NOT_NULL = "Longitude must not be null.";
	public static final String LONGITUDE_SIZE = "Longitude size must be between %1$d and %2$d.";
	public static final String DATETIME_NOT_NULL = "Datetime must not be null.";

//	InviteResource
	public static final String INVITE_NOT_FOUND = "Invite not found.";
	public static final String INVITE_ANSWERED = "Invite answered.";
	public static final String RESPONSE_NOT_NULL = "Response must not be null.";
	
//	RequestResource
	public static final String REQUEST_NOT_FOUND = "Request not found.";
	public static final String REQUEST_ANSWERED = "Request answered.";
	public static final String REQUESTER_INACTIVE = "Requester is not active.";

}
