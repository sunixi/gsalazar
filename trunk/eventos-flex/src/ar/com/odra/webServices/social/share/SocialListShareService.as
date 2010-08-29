package ar.com.odra.webServices.social.share
{
  public class SocialListShareService extends ShareBasicService {

	public function SocialListShareService(
		url:String, title:String ,text:String,
		lang:String = 'en', tags:String='', type:Number = 0, service:String = '', notes:String = ''){
		
		super();
		super.url = 'http://sociallist.org/submit.php?';
		
		params.title = title;
		params.text = text;
		params.lang = lang;
		params.tags = tags;
		params.type = type.toString();
		params.service = service;
		params.notes = notes;
		params.url = url;
	}
  }
}