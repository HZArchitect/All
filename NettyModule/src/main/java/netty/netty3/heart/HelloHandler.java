package netty.netty3.heart;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HelloHandler extends SimpleChannelHandler {

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		System.out.println(e.getMessage());
	}

	@Override
	public void handleUpstream(final ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		if (e instanceof IdleStateEvent) {
			/*SimpleDateFormat sdf = new SimpleDateFormat("MM:ss");
			IdleStateEvent event = (IdleStateEvent)e;
			System.out.println(event.getState() + "    " + sdf.format(new Date()));*/
			if (((IdleStateEvent) e).getState() == IdleState.ALL_IDLE) {
				System.out.println("踢 " + ctx.getChannel().getRemoteAddress()  + " 下线");
				//如果状态为读写超时则断开连接
				ChannelFuture write = ctx.getChannel().write("you will be closed!");
				write.addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						ctx.getChannel().close();
					}
				});

			}

		} else {
			super.handleUpstream(ctx, e);
		}
	}

	//	@Override
//	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//		System.out.println(e.getState() + "       " + simpleDateFormat.format(new Date()));
//	}
}
