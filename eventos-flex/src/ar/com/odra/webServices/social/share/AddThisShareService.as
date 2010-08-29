package ar.com.odra.webServices.social.share
{
  public class AddThisShareService extends ShareService {

	public function AddThisShareService(
		service:String, url:String, title:String,
		lang:String = 'en'){
		super();
		super.url = 'http://api.addthis.com/oexchange/0.8/forward/' + service + '/offer?';

		params.url = url;
		params.title = title;
		params.lang = lang;
		params.height = '560';
		params.width = '340';
		
	}
  }
}