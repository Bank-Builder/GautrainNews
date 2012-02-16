package za.co.bank_builder.adapter;

import java.net.URL;

import za.co.bank_builder.data.Tweet;
import za.co.bank_builder.news.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetAdapter extends BaseAdapter {
	private Context context;
	private Tweet[] tweets;
	
	public TweetAdapter ( Context context, Tweet[] tweets ) {
		this.context = context;
		this.tweets = tweets;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tweets.length;
	}

	@Override
	public Tweet getItem(int position) {
		// TODO Auto-generated method stub
		return tweets[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	//@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		 class ViewHolder {
		        TextView tweet_text;
		        TextView tweet_user;
		        ImageView tweet_avatar;
		    }
		 
		View v = convertView; 
        ViewHolder holder;

		if (v == null) {
			v = LayoutInflater.from(context).inflate(R.layout.main_list_item,null);
			holder = new ViewHolder();
			holder.tweet_text = (TextView) v.findViewById(R.id.main_list_item_text);
			holder.tweet_user = (TextView) v.findViewById(R.id.main_list_item_user);
			holder.tweet_avatar = (ImageView) v.findViewById(R.id.main_list_item_avatar);
			
			Tweet tweet = getItem(position);
			if (tweet != null) {
				holder.tweet_text.setText(tweet.getTweet());
				holder.tweet_user.setText(tweet.getUser());
				Bitmap bm = getBitmap(tweet.getAvatar());
				holder.tweet_avatar.setImageBitmap(bm);
				if(bm != null ) {
			        holder.tweet_avatar.setImageBitmap(bm);
			      } else holder.tweet_avatar.setImageResource( R.drawable.user_disabled);
			}
			v.setTag(holder);	
		} else holder = (ViewHolder) v.getTag();
		
			
		return v;
	}
	
	public Bitmap getBitmap(String bitmapUrl) {
		  try {
		    URL url = new URL(bitmapUrl);
		    return BitmapFactory.decodeStream(url.openConnection().getInputStream());
		  }
		  catch(Exception ex) {return null;}
		}
	
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

		int width = bm.getWidth();
		int height = bm.getHeight();

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();

		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

		return resizedBitmap;
		}
}
